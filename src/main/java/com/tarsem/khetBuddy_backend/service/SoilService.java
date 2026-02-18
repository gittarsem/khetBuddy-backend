package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.SoilDataResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SoilService {

    private final WebClient soilClient;
    private final WebClient meteoClient;

    private final ObjectMapper mapper = new ObjectMapper();

    public SoilService() {

        // SoilGrids API Client
        this.soilClient = WebClient.builder()
                .baseUrl("https://rest.isric.org")
                .build();

        // Open-Meteo API Client
        this.meteoClient = WebClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .build();
    }

    public SoilDataResponse getSoilData(double lat, double lon) {

        SoilDataResponse response = new SoilDataResponse();

        response.setNitrogen(fetchSoilProperty(lat, lon, "nitrogen"));
        response.setPhosphorus(fetchSoilProperty(lat, lon, "phosphorus"));
        response.setPotassium(fetchSoilProperty(lat, lon, "potassium"));

        response.setSoilMoisture(fetchSoilMoisture(lat, lon));

        return response;
    }

    private double fetchSoilProperty(double lat, double lon, String property) {

        try {
            String jsonResponse = soilClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/soilgrids/v2.0/properties/query")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("property", property)
                            .queryParam("depth", "0-5cm")
                            .queryParam("value", "mean")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = mapper.readTree(jsonResponse);

            return root.path("properties")
                    .path("layers")
                    .get(0)
                    .path("depths")
                    .get(0)
                    .path("values")
                    .path("mean")
                    .asDouble();

        } catch (Exception e) {

            System.out.println("⚠ Soil API failed for property: " + property);
            System.out.println("Using fallback default values...");

            return switch (property) {
                case "nitrogen" -> 50.0;
                case "phosphorus" -> 30.0;
                case "potassium" -> 40.0;
                default -> 20.0;
            };
        }
    }
    private double fetchSoilMoisture(double lat, double lon) {

        try {
            String jsonResponse = meteoClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/forecast")
                            .queryParam("latitude", lat)
                            .queryParam("longitude", lon)
                            .queryParam("hourly", "soil_moisture_0_to_1cm")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = mapper.readTree(jsonResponse);

            return root.path("hourly")
                    .path("soil_moisture_0_to_1cm")
                    .get(0)
                    .asDouble();

        } catch (Exception e) {

            System.out.println("⚠ Moisture API failed.");
            System.out.println("Using fallback moisture value...");

            return 0.25;
        }
    }
}

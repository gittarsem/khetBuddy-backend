package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.SoilDataResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SoilService {

    private final WebClient soilClient;
    private final WebClient meteoClient;

    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${SOIL_API}")
    private String apiKey;

    public SoilService(@Value("${SOIL_API}") String apiKey) {

        this.apiKey = apiKey;
        this.soilClient = WebClient.builder()
                .baseUrl("https://api.gooey.ai")
                .defaultHeader("Content-Type", "application/json")
                .build();

        this.meteoClient = WebClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .build();
    }

    public SoilDataResponse getSoilData(double lat, double lon) {

        SoilDataResponse response = new SoilDataResponse();

        response.setNitrogen(fetchSoilProperty(lat, lon, "nitrogen_total"));
        response.setPhosphorus(fetchSoilProperty(lat, lon, "phosphorous_extractable"));
        response.setPotassium(fetchSoilProperty(lat, lon, "potassium_extractable"));

        response.setSoilMoisture(fetchSoilMoisture(lat, lon));

        return response;
    }

    private double fetchSoilProperty(double lat, double lon, String property) {

        try {

            String requestBody = """
                    {
                      "lat": %f,
                      "lon": %f
                    }
                    """.formatted(lat, lon);

            String jsonResponse = soilClient.post()
                    .uri("/v2/functions?example_id=s1zad30sloo4")
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = mapper.readTree(jsonResponse);

            String valueWithUnit = root.path("output")
                    .path("return_value")
                    .path(property)
                    .asText();

            return Double.parseDouble(valueWithUnit.split(" ")[0]);

        } catch (Exception e) {

            System.out.println("⚠ Soil API failed for: " + property);

            return switch (property) {
                case "nitrogen_total" -> 50.0;
                case "phosphorous_extractable" -> 30.0;
                case "potassium_extractable" -> 40.0;
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

            JsonNode moistureArray = root.path("hourly")
                    .path("soil_moisture_0_to_1cm");

            double sum = 0;

            for (JsonNode val : moistureArray) {
                sum += val.asDouble();
            }

            return sum / moistureArray.size();


        } catch (Exception e) {

            System.out.println("⚠ Moisture API failed. Using default.");

            return 0.25;
        }
    }
}

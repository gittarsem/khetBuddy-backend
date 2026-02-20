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

    private final String apiKey;

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

        try {
            JsonNode returnValue = fetchSoilOnce(lat, lon);

            response.setNitrogen(extractValue(returnValue, "nitrogen_total", true));
            response.setPhosphorus(extractValue(returnValue, "phosphorous_extractable", false));
            response.setPotassium(extractValue(returnValue, "potassium_extractable", false));

        } catch (Exception e) {
            response.setNitrogen(50);
            response.setPhosphorus(30);
            response.setPotassium(40);
        }

        response.setSoilMoisture(fetchSoilMoisture(lat, lon));

        return response;
    }

    private JsonNode fetchSoilOnce(double lat, double lon) throws Exception {

        String jsonResponse = soilClient.post()
                .uri("/v2/functions?example_id=s1zad30sloo4")
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue("""
                        {
                          "lat": %f,
                          "lon": %f
                        }
                        """.formatted(lat, lon))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode root = mapper.readTree(jsonResponse);

        return root.path("output").path("return_value");
    }

    private double extractValue(JsonNode returnValue, String property, boolean scaleNitrogen) {

        String valueWithUnit = returnValue.path(property).asText();

        double value = Double.parseDouble(valueWithUnit.split(" ")[0]);

        if (scaleNitrogen) {
            value = value * 100;
        }

        return value;
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
            return 0.25;
        }
    }
}

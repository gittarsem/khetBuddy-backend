package com.tarsem.khetBuddy_backend.service;


import com.tarsem.khetBuddy_backend.dto.SoilDataResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SoilService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

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
            String url =
                    "https://rest.isric.org/soilgrids/v2.0/properties/query" +
                            "?lat=" + lat +
                            "&lon=" + lon +
                            "&property=" + property +
                            "&depth=0-5cm" +
                            "&value=mean";

            String jsonResponse = restTemplate.getForObject(url, String.class);

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
            e.printStackTrace();
            return -1;
        }
    }
    private double fetchSoilMoisture(double lat, double lon) {

        try {
            String url =
                    "https://api.open-meteo.com/v1/forecast" +
                            "?latitude=" + lat +
                            "&longitude=" + lon +
                            "&hourly=soil_moisture_0_to_1cm";

            String jsonResponse = restTemplate.getForObject(url, String.class);

            JsonNode root = mapper.readTree(jsonResponse);

            // Take first moisture value
            return root.path("hourly")
                    .path("soil_moisture_0_to_1cm")
                    .get(0)
                    .asDouble();

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

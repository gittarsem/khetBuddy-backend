package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.LocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class LocationService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public LocationResponse getInfo(double lat, double lon) {

        LocationResponse response = new LocationResponse();

        try {
            String url =
                    "https://nominatim.openstreetmap.org/reverse" +
                            "?lat=" + lat +
                            "&lon=" + lon +
                            "&format=json";

            String json = restTemplate.getForObject(url, String.class);

            JsonNode root = mapper.readTree(json);

            JsonNode address = root.path("address");

            response.setState(address.path("state").asText());
            response.setDistrict(address.path("state_district").asText());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}

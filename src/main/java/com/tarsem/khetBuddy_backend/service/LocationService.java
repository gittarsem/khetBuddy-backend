package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.LocationResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LocationService {

    private final WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public LocationService() {

        this.webClient = WebClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader(HttpHeaders.USER_AGENT, "khetBuddyApp/1.0 (work4tarsemgulab@gmail.com)")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public LocationResponse getInfo(double lat, double lon) {

        LocationResponse response = new LocationResponse();

        try {
            String json = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/reverse")
                            .queryParam("lat", lat)
                            .queryParam("lon", lon)
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = mapper.readTree(json);
            JsonNode address = root.path("address");

            response.setState(address.path("state").asText());
            response.setDistrict(address.path("state_district").asText());

        } catch (Exception e) {
            System.out.println("⚠ Location API failed. Using fallback district...");
            response.setDistrict("Unknown");
        }

        return response;
    }
}

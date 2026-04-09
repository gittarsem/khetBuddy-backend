package com.tarsem.khetBuddy_backend.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarsem.khetBuddy_backend.dto.weather.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherClient {

    private final WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.open-meteo.com")
                .build();
    }

    public WeatherResponse getWeather(double lat, double lon) {
        try {
            String rawJson = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/forecast")
                            .queryParam("latitude", lat)
                            .queryParam("longitude", lon)
                            .queryParam("current", "temperature_2m,relative_humidity_2m,windspeed_10m")
                            .queryParam("timezone", "auto")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = mapper.readTree(rawJson);

            WeatherResponse response = new WeatherResponse();

            JsonNode current = root.get("current");

            double temp = current.get("temperature_2m").asDouble();
            double humidity = current.get("relative_humidity_2m").asDouble();
            double windSpeed = current.get("windspeed_10m").asDouble();

            response.setCurrentTemperature(temp);
            response.setHumidity(humidity);
            response.setWindSpeed(windSpeed);

            response.setAdvisory(generateAdvisory(temp, humidity, windSpeed));

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data", e);
        }
    }

    private String generateAdvisory(double temp, double humidity, double wind) {
        if (humidity > 85) {
            return "High humidity - Risk of fungal diseases. Monitor crops closely.";
        }
        if (temp > 35) {
            return "High temperature - Increase irrigation to prevent crop stress.";
        }
        if (wind > 25) {
            return "Strong wind - Avoid pesticide spraying.";
        }
        if (humidity < 30) {
            return "Low humidity - Soil may dry quickly. Consider irrigation.";
        }
        return "Weather is stable - Suitable for farming activities.";
    }
}
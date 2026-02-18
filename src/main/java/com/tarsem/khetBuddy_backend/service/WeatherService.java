package com.tarsem.khetBuddy_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarsem.khetBuddy_backend.dto.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    private final WebClient webClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherService() {
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
                            .queryParam("daily", "temperature_2m_mean,rain_sum")
                            .queryParam("hourly", "relative_humidity_2m,windspeed_10m")
                            .queryParam("timezone", "auto")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode root = mapper.readTree(rawJson);

            WeatherResponse response = new WeatherResponse();

            JsonNode daily = root.get("daily");

            double avgTemp = daily.get("temperature_2m_mean").get(0).asDouble();
            response.setAvgTemperature(avgTemp);

            JsonNode rainArray = daily.get("rain_sum");

            double rainfallToday = rainArray.get(0).asDouble();
            response.setRainfallToday(rainfallToday);

            double totalRain = 0;
            for (JsonNode r : rainArray) {
                totalRain += r.asDouble();
            }
            response.setTotalRainfall(totalRain);

            JsonNode hourly = root.get("hourly");

            double humidity = hourly.get("relative_humidity_2m").get(0).asDouble();
            response.setHumidity(humidity);

            double windSpeed = hourly.get("windspeed_10m").get(0).asDouble();
            response.setWindSpeed(windSpeed);

            response.setAdvisory(generateAdvisory(rainfallToday, humidity, windSpeed));

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data", e);
        }
    }

    private String generateAdvisory(double rain, double humidity, double wind) {

        if (rain > 20) {
            return "Heavy rain expected - Avoid irrigation today.";
        }

        if (humidity > 80) {
            return "High humidity - Risk of fungal disease.";
        }

        if (wind > 25) {
            return "Strong wind - Avoid pesticide spraying.";
        }

        return "Weather is normal - Suitable for farming activities.";
    }
}

package com.tarsem.khetBuddy_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarsem.khetBuddy_backend.dto.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public WeatherResponse getWeather(double lat, double lon) {

        String url =
                "https://api.open-meteo.com/v1/forecast?"
                        + "latitude=" + lat
                        + "&longitude=" + lon
                        + "&daily=temperature_2m_mean,rain_sum"
                        + "&hourly=relative_humidity_2m,windspeed_10m"
                        + "&timezone=auto";

        String rawJson = restTemplate.getForObject(url, String.class);

        try {
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
            throw new RuntimeException("Error parsing weather response", e);
        }
    }

    private String generateAdvisory(double rain, double humidity, double wind) {

        if (rain > 20) {
            return "Heavy rain expected 🌧️ Avoid irrigation today.";
        }

        if (humidity > 80) {
            return "High humidity 💧 Risk of fungal disease.";
        }

        if (wind > 25) {
            return "Strong wind 🌬️ Avoid pesticide spraying.";
        }

        return "Weather is normal ✅ Suitable for farming activities.";
    }
}

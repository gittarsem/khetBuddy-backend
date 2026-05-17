package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.weather.WeatherResponse;
import com.tarsem.khetBuddy_backend.external.WeatherClient;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather API", description = "APIs for fetching weather information")
public class WeatherController {

    @Autowired
    private WeatherClient weatherClient;

    @Operation(
            summary = "Get current weather",
            description = "Fetches current weather details using latitude and longitude"
    )
    @GetMapping("/current")
    public WeatherResponse getWeather(

            @Parameter(
                    description = "Latitude of location",
                    example = "28.7041"
            )
            @RequestParam double lat,

            @Parameter(
                    description = "Longitude of location",
                    example = "77.1025"
            )
            @RequestParam double lon
    ) {

        return weatherClient.getWeather(lat, lon);
    }
}
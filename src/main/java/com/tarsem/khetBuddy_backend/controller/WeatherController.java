package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.weather.WeatherResponse;
import com.tarsem.khetBuddy_backend.external.WeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherClient weatherClient;

    @GetMapping("/current")
    public WeatherResponse getWeather(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        return weatherClient.getWeather(lat, lon);
    }
}

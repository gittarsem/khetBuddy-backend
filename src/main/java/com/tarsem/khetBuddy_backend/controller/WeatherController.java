package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.WeatherResponse;
import com.tarsem.khetBuddy_backend.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current")
    public WeatherResponse getWeather(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        return weatherService.getWeather(lat, lon);
    }
}

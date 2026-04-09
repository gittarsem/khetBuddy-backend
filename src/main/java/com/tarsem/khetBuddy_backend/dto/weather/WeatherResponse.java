package com.tarsem.khetBuddy_backend.dto.weather;

import lombok.Data;

@Data
public class WeatherResponse {

    private double currentTemperature;
    private double humidity;
    private double windSpeed;
    private String advisory;
}

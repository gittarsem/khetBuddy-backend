package com.tarsem.khetBuddy_backend.dto;

import lombok.Data;

@Data
public class WeatherResponse {

    private double avgTemperature;
    private double humidity;
    private double rainfallToday;
    private double totalRainfall;
    private double windSpeed;
    private String advisory;
}

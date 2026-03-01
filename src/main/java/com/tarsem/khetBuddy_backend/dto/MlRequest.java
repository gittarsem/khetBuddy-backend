package com.tarsem.khetBuddy_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MlRequest {
    @JsonProperty("crop_type")
    private String cropType;

    private String season;
    private String district;

    private double nitrogen;
    private double phosphorus;
    private double potassium;

    @JsonProperty("soil_ph")
    private double soilPh;

    @JsonProperty("soil_moisture")
    private float soilMoisture;

    @JsonProperty("irrigation_type")
    private String irrigationType;

    @JsonProperty("avg_temperature")
    private double avgTemperature;

    @JsonProperty("total_rainfall")
    private double totalRainfall;

    private double humidity;
}

package com.tarsem.khetBuddy_backend.dto.Fertilizer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FertilizerMlRequest {

    @JsonProperty("crop_type")
    private String cropType;

    @JsonProperty("growth_stage")
    private String growthStage;

    @JsonProperty("soil_n")
    private int nitrogen;

    @JsonProperty("soil_p")
    private int phosphorus;

    @JsonProperty("soil_k")
    private int potassium;

    @JsonProperty("soil_ph")
    private double ph;

    private double temperature;

    private double rainfall;

    private double humidity;

    @JsonProperty("irrigation_type")
    private String irrigationType;
}

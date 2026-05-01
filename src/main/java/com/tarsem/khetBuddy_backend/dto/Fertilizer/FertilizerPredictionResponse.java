package com.tarsem.khetBuddy_backend.dto.Fertilizer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FertilizerPredictionResponse {
    @JsonProperty("recommended_N")
    private double recommendedN;

    @JsonProperty("recommended_P")
    private double recommendedP;

    @JsonProperty("recommended_K")
    private double recommendedK;

    @JsonProperty("deficiency_risk")
    private String deficiencyRisk;

    @JsonProperty("nutrient_deficiency")
    private NutrientDeficiencyDTO nutrientDeficiency;

    @JsonProperty("application_time")
    private String applicationTime;

    @JsonProperty("crop_type")
    private String cropType;

    @JsonProperty("growth_stage")
    private String growthStage;
}

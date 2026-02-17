package com.tarsem.khetBuddy_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MlResponse {
    @JsonProperty("crop_type")
    private String cropType;

    private String district;
    private String season;

    @JsonProperty("yield_per_hectare")
    private YieldPerHectare yieldPerHectare;

    private String unit;

    @JsonProperty("confidence_note")
    private String confidenceNote;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class YieldPerHectare {
        private double lower;
        private double expected;
        private double higher;


    }
}

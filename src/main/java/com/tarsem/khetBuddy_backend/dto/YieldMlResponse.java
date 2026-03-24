package com.tarsem.khetBuddy_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YieldMlResponse {

    @JsonProperty("crop_type")
    private String cropType;

    private String season;

    private Location location;

    private Soil soil;

    @JsonProperty("yield_per_hectare")
    private YieldPerHectare yieldPerHectare;

    private String unit;

    @JsonProperty("confidence_note")
    private String confidenceNote;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {

        private String district;
        private String state;
        private double latitude;
        private double longitude;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Soil {

        private double nitrogen;
        private double phosphorus;
        private double potassium;

        @JsonProperty("soil_ph")
        private double soilPh;

        @JsonProperty("soil_moisture")
        private double soilMoisture;

        private String source;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class YieldPerHectare {

        private double lower;
        private double expected;
        private double higher;
    }
}
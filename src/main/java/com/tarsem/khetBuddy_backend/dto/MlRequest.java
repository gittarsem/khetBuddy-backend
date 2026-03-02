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

    @JsonProperty("irrigation_type")
    private String irrigationType;

    public Double latitude;
    public Double longitude;
}

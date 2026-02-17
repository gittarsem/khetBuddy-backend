package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoilDataResponse {
    private double nitrogen;
    private double phosphorus;
    private double potassium;
    private double soilMoisture;
}

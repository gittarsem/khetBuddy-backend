package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IrrigationPlanResponseDTO {

    private boolean irrigateToday;
    private double hoursRequired;
    private int cycles;
    private String message;

}
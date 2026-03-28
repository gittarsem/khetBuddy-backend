package com.tarsem.khetBuddy_backend.dto.irrigation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDTO {
    private int totalDays;
    private int totalIrrigationDays;
    private double totalHours;
    private String message;
}

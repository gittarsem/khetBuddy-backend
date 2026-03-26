package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLScheduleResult {
    private boolean emergency;
    private double totalIrrigationMm;
    private List<MLDayPlan> days;
}

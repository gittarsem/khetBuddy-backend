package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IrrigationScheduleDTO {

    private double totalHours;
    private int totalCycles;
    private List<DayPlan> days;
}

package com.tarsem.khetBuddy_backend.dto.irrigation;

import com.tarsem.khetBuddy_backend.dto.farmer.FarmerDayPlan;
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
    private List<FarmerDayPlan> days;
}

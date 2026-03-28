package com.tarsem.khetBuddy_backend.dto.irrigation;

import com.tarsem.khetBuddy_backend.dto.farmer.FarmerDayPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerScheduleResponse {
    private SummaryDTO summary;
    private List<FarmerDayPlan> plan;
}

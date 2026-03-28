package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.irrigation.FarmerScheduleResponse;
import com.tarsem.khetBuddy_backend.dto.irrigation.IrrigationAdviceDTO;
import com.tarsem.khetBuddy_backend.dto.irrigation.IrrigationPlanRequestDTO;

public interface IrrigationPlanService {
    IrrigationAdviceDTO getImmediatePlan(Long farmId, IrrigationPlanRequestDTO requestDTO);

    FarmerScheduleResponse generateSchedule(Long farmId, IrrigationPlanRequestDTO requestDTO);
}

package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.FarmerScheduleResponse;
import com.tarsem.khetBuddy_backend.dto.IrrigationAdviceDTO;
import com.tarsem.khetBuddy_backend.dto.IrrigationPlanRequestDTO;
import com.tarsem.khetBuddy_backend.dto.IrrigationPlanResponseDTO;
import org.jspecify.annotations.Nullable;

public interface IrrigationPlanService {
    IrrigationAdviceDTO getImmediatePlan(Long farmId, IrrigationPlanRequestDTO requestDTO);

    FarmerScheduleResponse generateSchedule(Long farmId, IrrigationPlanRequestDTO requestDTO);
}

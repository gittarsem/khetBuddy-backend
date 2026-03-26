package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.IrrigationPlanRequestDTO;
import com.tarsem.khetBuddy_backend.dto.IrrigationPlanResponseDTO;
import org.jspecify.annotations.Nullable;

public interface IrrigationPlanService {
    void getImmediatePlan(Long farmId, IrrigationPlanRequestDTO requestDTO);
}

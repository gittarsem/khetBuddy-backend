package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.irrigation.IrrigationMLRequestDTo;
import com.tarsem.khetBuddy_backend.dto.irrigation.MLImmediateResult;
import com.tarsem.khetBuddy_backend.dto.irrigation.MLScheduleResult;

public interface IrrigationMLService {
    MLImmediateResult getImmediateRecommendation(IrrigationMLRequestDTo request);

    MLScheduleResult getSchedule(IrrigationMLRequestDTo request);
}

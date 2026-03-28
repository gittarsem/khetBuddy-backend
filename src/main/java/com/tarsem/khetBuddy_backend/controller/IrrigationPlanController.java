package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.irrigation.FarmerScheduleResponse;
import com.tarsem.khetBuddy_backend.dto.irrigation.IrrigationAdviceDTO;
import com.tarsem.khetBuddy_backend.dto.irrigation.IrrigationPlanRequestDTO;
import com.tarsem.khetBuddy_backend.service.Interfaces.FarmerService;
import com.tarsem.khetBuddy_backend.service.Interfaces.IrrigationPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/irrigation")
public class IrrigationPlanController {

    @Autowired
    public IrrigationPlanService irrigationService;

    @Autowired
    public FarmerService farmerService;

    @PostMapping("/immediate/{farmId}")
    public ResponseEntity<IrrigationAdviceDTO> getImmediatePlan(
            @PathVariable Long farmId,
            @RequestBody IrrigationPlanRequestDTO requestDTO
    ){
        return ResponseEntity.ok(irrigationService.getImmediatePlan(farmId,requestDTO));
    }

    @PostMapping("/schedule/{farmId}")
    public ResponseEntity<FarmerScheduleResponse> generateSchedule(
            @PathVariable Long farmId,
            @RequestBody IrrigationPlanRequestDTO requestDTO
    ){
        return ResponseEntity.ok(irrigationService.generateSchedule(farmId,requestDTO));
    }
}

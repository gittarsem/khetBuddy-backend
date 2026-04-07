package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.irrigation.FarmerScheduleResponse;
import com.tarsem.khetBuddy_backend.dto.irrigation.IrrigationAdviceDTO;
import com.tarsem.khetBuddy_backend.dto.irrigation.IrrigationPlanRequestDTO;
import com.tarsem.khetBuddy_backend.service.Interfaces.FarmerService;
import com.tarsem.khetBuddy_backend.service.Interfaces.IrrigationPlanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/irrigation")
@Tag(name = "Irrigation Planning", description = "APIs for irrigation advice and scheduling")
public class IrrigationPlanController {

    @Autowired
    public IrrigationPlanService irrigationService;

    @Autowired
    public FarmerService farmerService;

    @Operation(
            summary = "Get Immediate Irrigation Plan",
            description = "Returns real-time irrigation advice based on farm conditions"
    )
    @PostMapping("/immediate/{farmId}")
    public ResponseEntity<IrrigationAdviceDTO> getImmediatePlan(
            @Parameter(description = "Unique ID of the farm", example = "1")
            @PathVariable Long farmId,

            @RequestBody IrrigationPlanRequestDTO requestDTO
    ){
        return ResponseEntity.ok(irrigationService.getImmediatePlan(farmId, requestDTO));
    }

    @Operation(
            summary = "Generate Irrigation Schedule",
            description = "Creates a future irrigation schedule for the farmer"
    )
    @PostMapping("/schedule/{farmId}")
    public ResponseEntity<FarmerScheduleResponse> generateSchedule(
            @Parameter(description = "Unique ID of the farm", example = "1")
            @PathVariable Long farmId,

            @RequestBody IrrigationPlanRequestDTO requestDTO
    ){
        return ResponseEntity.ok(irrigationService.generateSchedule(farmId, requestDTO));
    }
}
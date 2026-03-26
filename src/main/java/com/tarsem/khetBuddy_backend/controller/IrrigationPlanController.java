package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.IrrigationPlanRequestDTO;
import com.tarsem.khetBuddy_backend.service.Interfaces.FarmerService;
import com.tarsem.khetBuddy_backend.service.Interfaces.IrrigationPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void generatePlan(
            @PathVariable Long farmId,
            @RequestBody IrrigationPlanRequestDTO requestDTO
    ){
        irrigationService.getImmediatePlan(farmId,requestDTO);
    }
}

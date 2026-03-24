package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.UserEntity;
import com.tarsem.khetBuddy_backend.repo.FarmRepo;
import com.tarsem.khetBuddy_backend.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/yield")
@Tag(name = "Yield Prediction Controller", description = "APIs for crop yield prediction and history tracking")
public class YieldPredictionController {

    @Autowired
    private YieldPredictionService yieldPredictionService;

    @Autowired
    private UserService userService;

    @Autowired
    private FarmRepo farmRepo;


    @PostMapping("/predict/{farmId}")
    @Operation(
            summary = "Predict crop yield",
            description = "Generates yield prediction for a specific farm using its details"
    )
    public YieldMlResponse predictYield(@PathVariable Long farmId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("AUTH = " + auth);
        if (auth != null) {
            System.out.println("AUTHORITIES = " + auth.getAuthorities());
        }

        UserEntity userEntity = userService.getCurrentUser();

        Farm farm=farmRepo.findById(farmId)
                .orElseThrow(()-> new RuntimeException("Farm does not exist"));
        Double latitude = farm.getLatitude();
        Double longitude = farm.getLongitude();

        YieldMlRequest yieldMlRequest = new YieldMlRequest();
        yieldMlRequest.setCropType(farm.getCrop());
        yieldMlRequest.setIrrigationType(farm.getIrrigationType());
        yieldMlRequest.setLatitude(latitude);
        yieldMlRequest.setLongitude(longitude);
        System.out.println(yieldMlRequest);
        return yieldPredictionService.predict(yieldMlRequest,farmId);
    }

    @GetMapping("/history/{farmId}")
    @Operation(
            summary = "Get prediction history",
            description = "Fetches paginated history of yield predictions for a farm"
    )
    public Page<YieldPredictionDTO> getHistory(
            @PathVariable Long farmId,
            @RequestParam int page,
            @RequestParam int size
    ){
        System.out.println("API HIT");
        return yieldPredictionService.getHistory(farmId,page,size);
    }
}
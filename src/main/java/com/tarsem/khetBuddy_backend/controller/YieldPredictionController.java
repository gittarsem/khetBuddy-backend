package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.User;
import com.tarsem.khetBuddy_backend.repo.FarmRepo;
import com.tarsem.khetBuddy_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/yield")
public class YieldPredictionController {

    @Autowired
    private YieldPredictionService yieldPredictionService;

    @Autowired
    private UserService userService;

    @Autowired
    private FarmRepo farmRepo;


    @PostMapping("/predict/{farmId}")
    public MlResponse predictYield(@PathVariable Long farmId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("AUTH = " + auth);
        if (auth != null) {
            System.out.println("AUTHORITIES = " + auth.getAuthorities());
        }

        User user = userService.getCurrentUser();

        Farm farm=farmRepo.findById(farmId)
                .orElseThrow(()-> new RuntimeException("Farm does not exist"));
        Double latitude = farm.getLatitude();
        Double longitude = farm.getLongitude();

        MlRequest mlRequest = new MlRequest();
        mlRequest.setCropType(farm.getCrop());
        mlRequest.setIrrigationType(farm.getIrrigationType());
        mlRequest.setLatitude(latitude);
        mlRequest.setLongitude(longitude);
        System.out.println(mlRequest);
        return yieldPredictionService.predict(mlRequest,farmId);
    }
}

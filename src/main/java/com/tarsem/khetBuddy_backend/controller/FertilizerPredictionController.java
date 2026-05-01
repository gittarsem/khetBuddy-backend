package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionRequest;
import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionResponse;
import com.tarsem.khetBuddy_backend.service.Interfaces.FertilizerPredictionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/fertilizer")
@AllArgsConstructor
public class FertilizerPredictionController {

    private FertilizerPredictionService predictionService;

    @PostMapping("/predict/{farmId}")
    public ResponseEntity<FertilizerPredictionResponse> prediction(@RequestBody FertilizerPredictionRequest request,
                                                                   @PathVariable Long farmId) throws AccessDeniedException {

        return ResponseEntity.ok(predictionService.predictFertilizer(request,farmId));
    }
}

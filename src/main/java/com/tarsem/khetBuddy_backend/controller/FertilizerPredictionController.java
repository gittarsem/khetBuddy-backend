package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionRequest;
import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionResponse;
import com.tarsem.khetBuddy_backend.service.Interfaces.FertilizerPredictionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/fertilizer")
@AllArgsConstructor
@Tag(
        name = "Fertilizer Prediction",
        description = "APIs for predicting suitable fertilizers for crops"
)
public class FertilizerPredictionController {

    private FertilizerPredictionService predictionService;

    @Operation(
            summary = "Predict fertilizer recommendation",
            description = "Predicts the most suitable fertilizer based on farm and soil details"
    )
    @PostMapping("/predict/{farmId}")
    public ResponseEntity<FertilizerPredictionResponse> prediction(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Fertilizer prediction request details"
            )
            @RequestBody FertilizerPredictionRequest request,

            @Parameter(
                    description = "Unique ID of the farm",
                    example = "1"
            )
            @PathVariable Long farmId

    ) throws AccessDeniedException {

        return ResponseEntity.ok(
                predictionService.predictFertilizer(request, farmId)
        );
    }
}
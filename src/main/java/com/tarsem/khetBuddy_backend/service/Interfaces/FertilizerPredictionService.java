package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionRequest;
import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionResponse;
import org.jspecify.annotations.Nullable;


import java.nio.file.AccessDeniedException;

public interface FertilizerPredictionService {
    @Nullable FertilizerPredictionResponse predictFertilizer(FertilizerPredictionRequest request, Long farmId) throws AccessDeniedException;
}

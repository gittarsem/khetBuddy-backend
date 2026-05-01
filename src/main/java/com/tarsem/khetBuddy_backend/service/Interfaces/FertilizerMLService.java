package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerMlRequest;
import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionResponse;
import org.jspecify.annotations.Nullable;

public interface FertilizerMLService {
    @Nullable FertilizerPredictionResponse predict(FertilizerMlRequest fertilizerMlRequest);
}

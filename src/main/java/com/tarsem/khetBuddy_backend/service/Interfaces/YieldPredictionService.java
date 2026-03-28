package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.yield.YieldMlRequest;
import com.tarsem.khetBuddy_backend.dto.yield.YieldMlResponse;
import com.tarsem.khetBuddy_backend.dto.yield.YieldPredictionDTO;
import org.springframework.data.domain.Page;

public interface YieldPredictionService {
    YieldMlResponse predict(YieldMlRequest request, Long farmId);

    void saveMlResponse(YieldMlResponse response, Long farmId);

    Page<YieldPredictionDTO> getHistory(Long farmId, int page, int size);
}

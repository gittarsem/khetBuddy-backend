package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.MlRequest;
import com.tarsem.khetBuddy_backend.dto.MlResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YieldPredictionService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String ML_API_URL =
            "https://yeild-prediction-api.onrender.com/predict";

    public MlResponse predict(MlRequest request) {

        return restTemplate.postForObject(
                ML_API_URL,
                request,
                MlResponse.class
        );
    }
}


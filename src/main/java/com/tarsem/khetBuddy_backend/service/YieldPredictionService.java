package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.MlRequest;
import com.tarsem.khetBuddy_backend.dto.MlResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Service
public class YieldPredictionService {

    private final WebClient webClient;

    public YieldPredictionService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://yeild-prediction-api.onrender.com")
                .build();
    }

    public MlResponse predict(MlRequest request) {
        try {
            return webClient.post()
                    .uri("/api/predict")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(MlResponse.class)
                    .timeout(Duration.ofSeconds(15))
                    .retry(2)
                    .block();
        }
        catch (WebClientResponseException e) {
            System.out.println("ML API ERROR:");
            System.out.println(e.getResponseBodyAsString());
            throw e;
        }

    }
}

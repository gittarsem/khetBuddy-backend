package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerMlRequest;
import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionResponse;
import com.tarsem.khetBuddy_backend.service.Interfaces.FertilizerMLService;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FertilizerMLServiceImpl implements FertilizerMLService {

    private final WebClient webClient;

    public FertilizerMLServiceImpl(WebClient.Builder builder){
        this.webClient=builder
                .baseUrl("https://fertilizer-recommendation-api-ul60.onrender.com")
                .build();
    }


    public  FertilizerPredictionResponse predict(FertilizerMlRequest fertilizerMlRequest) {
        return mapFertilizerResponse("/predict-fertilizer",fertilizerMlRequest);
    }

    private FertilizerPredictionResponse mapFertilizerResponse(String uri, FertilizerMlRequest fertilizerMlRequest) {
        try {
            return webClient.post()
                    .uri(uri)
                    .bodyValue(fertilizerMlRequest)
                    .retrieve()
                    .bodyToMono(FertilizerPredictionResponse.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Ml API Failed "+e.getMessage());
        }
    }


}

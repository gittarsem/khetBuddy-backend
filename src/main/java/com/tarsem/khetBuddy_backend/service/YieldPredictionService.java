package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.MlRequest;
import com.tarsem.khetBuddy_backend.dto.MlResponse;
import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.YieldPrediction;
import com.tarsem.khetBuddy_backend.repo.FarmRepo;
import com.tarsem.khetBuddy_backend.repo.YieldPredictionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class YieldPredictionService {

    @Autowired
    private YieldPredictionRepo yieldPredictionRepo;

    @Autowired
    private FarmRepo farmRepo;

    private final WebClient webClient;

    public YieldPredictionService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://yeild-prediction-api.onrender.com")
                .build();
    }

    public MlResponse predict(MlRequest request,Long farmId) {
        try {
            MlResponse response= webClient.post()
                    .uri("/api/predict")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(MlResponse.class)
                    .timeout(Duration.ofSeconds(60))
                    .retry(1)
                    .block();

            if(response!=null)saveMlResponse(response,farmId);
            return response;
        }
        catch (WebClientResponseException e) {
            System.out.println("ML API ERROR:");
            System.out.println(e.getResponseBodyAsString());
            throw e;
        }

    }

    public void saveMlResponse(MlResponse response,Long farmId){
        YieldPrediction entity = new YieldPrediction();
        Farm farm=farmRepo.findById(farmId)
                .orElseThrow(()-> new RuntimeException("Farm not found"));

        entity.setCropType(response.getCropType());
        entity.setSeason(response.getSeason());
        entity.setFarm(farm);
        entity.setDistrict(response.getLocation().getDistrict());
        entity.setState(response.getLocation().getState());
        entity.setLatitude(response.getLocation().getLatitude());
        entity.setLongitude(response.getLocation().getLongitude());

        entity.setNitrogen(response.getSoil().getNitrogen());
        entity.setPhosphorus(response.getSoil().getPhosphorus());
        entity.setPotassium(response.getSoil().getPotassium());
        entity.setSoilPh(response.getSoil().getSoilPh());
        entity.setSoilMoisture(response.getSoil().getSoilMoisture());

        entity.setYieldLower(response.getYieldPerHectare().getLower());
        entity.setYieldExpected(response.getYieldPerHectare().getExpected());
        entity.setYieldHigher(response.getYieldPerHectare().getHigher());

        entity.setUnit(response.getUnit());
        entity.setConfidenceNote(response.getConfidenceNote());

        entity.setCreatedAt(LocalDateTime.now());

        yieldPredictionRepo.save(entity);
    }
}

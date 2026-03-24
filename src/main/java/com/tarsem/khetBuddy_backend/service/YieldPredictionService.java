package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.YieldMlRequest;
import com.tarsem.khetBuddy_backend.dto.YieldMlResponse;
import com.tarsem.khetBuddy_backend.dto.YieldPredictionDTO;
import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.YieldPrediction;
import com.tarsem.khetBuddy_backend.repo.FarmRepo;
import com.tarsem.khetBuddy_backend.repo.YieldPredictionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public YieldMlResponse predict(YieldMlRequest request, Long farmId) {
        try {
            YieldMlResponse response= webClient.post()
                    .uri("/api/predict")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(YieldMlResponse.class)
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

    public void saveMlResponse(YieldMlResponse response, Long farmId){
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

    public Page<YieldPredictionDTO> getHistory(Long farmId, int page, int size) {
        Page<YieldPrediction> pageData= yieldPredictionRepo.findByFarmId(farmId,
                PageRequest.of(page,size, Sort.by("createdAt").descending()));
        return pageData.map(this::convertToDTO);
    }

    private YieldPredictionDTO convertToDTO(YieldPrediction yieldPrediction) {
        YieldPredictionDTO dto=new YieldPredictionDTO();
        dto.setId(yieldPrediction.getId());
        dto.setYieldLower(yieldPrediction.getYieldLower());
        dto.setYieldHigher(yieldPrediction.getYieldHigher());
        dto.setYieldExpected(yieldPrediction.getYieldExpected());
        dto.setCropType(yieldPrediction.getCropType());
        dto.setCreatedAt(yieldPrediction.getCreatedAt());
        return dto;
    }
}

package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerMlRequest;
import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionRequest;
import com.tarsem.khetBuddy_backend.dto.Fertilizer.FertilizerPredictionResponse;
import com.tarsem.khetBuddy_backend.dto.weather.WeatherResponse;
import com.tarsem.khetBuddy_backend.entity.Farm;
import com.tarsem.khetBuddy_backend.entity.UserEntity;
import com.tarsem.khetBuddy_backend.exception.ResourceNotFoundException;
import com.tarsem.khetBuddy_backend.external.WeatherClient;
import com.tarsem.khetBuddy_backend.repo.FarmRepo;
import com.tarsem.khetBuddy_backend.service.Interfaces.FertilizerMLService;
import com.tarsem.khetBuddy_backend.service.Interfaces.FertilizerPredictionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

import static com.tarsem.khetBuddy_backend.Utils.AppUtils.giveMeCurrentUser;
import static com.tarsem.khetBuddy_backend.Utils.IrrigationUtility.getCropStage;

@Service
@RequiredArgsConstructor
public class FertilizerPredictionServiceImpl implements FertilizerPredictionService {

    private final FarmRepo farmRepo;
    private final ModelMapper modelMapper;
    private final FertilizerMLService mlService;
    private final WeatherClient weatherClient;

    @Override
    public FertilizerPredictionResponse predictFertilizer(FertilizerPredictionRequest request, Long farmId)
            throws AccessDeniedException {

        UserEntity user = giveMeCurrentUser();

        Farm farm = farmRepo.findById(farmId)
                .orElseThrow(() -> new ResourceNotFoundException("Farm does not exist"));

        validateOwnership(farm, user);

        FertilizerMlRequest mlRequest = buildMlRequest(request, farm);
        enrichWithWeather(mlRequest, farm);
        System.out.println(mlRequest);
        LocalDate sowingDate = farm.getSowing_date() != null
                ? farm.getSowing_date()
                : LocalDate.of(2026, 3, 1);
        return mlService.predict(mlRequest);
    }

    private void validateOwnership(Farm farm, UserEntity user) throws AccessDeniedException {
        if (!farm.getUserEntity().getId().equals(user.getId())) {
            throw new AccessDeniedException("Farm is not related to User");
        }
    }

    private FertilizerMlRequest buildMlRequest(FertilizerPredictionRequest request, Farm farm) {
        FertilizerMlRequest mlRequest = modelMapper.map(request, FertilizerMlRequest.class);

        mlRequest.setCropType(farm.getCrop());
        mlRequest.setGrowthStage(getCropStage(farm.getCrop(), farm.getSowing_date()));
        mlRequest.setPh(farm.getPhLevel());
        LocalDate sowingDate = farm.getSowing_date() != null
                ? farm.getSowing_date()
                : LocalDate.of(2026, 3, 1);
        return mlRequest;
    }

    private void enrichWithWeather(FertilizerMlRequest mlRequest, Farm farm) {
        WeatherResponse weather = weatherClient.getWeather(farm.getLatitude(), farm.getLongitude());

        mlRequest.setTemperature(weather.getCurrentTemperature());
        mlRequest.setHumidity(weather.getHumidity());
        mlRequest.setRainfall(weather.getRainfall());
    }
}

package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.User;
import com.tarsem.khetBuddy_backend.repo.FarmRepo;
import com.tarsem.khetBuddy_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/yield")
public class YieldPredictionController {

    @Autowired
    private YieldPredictionService yieldPredictionService;

    @Autowired
    private UserService userService;

    @Autowired
    private SoilService soilService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private FarmRepo farmRepo;

    public static String getSeason() {

        int month = LocalDate.now().getMonthValue();

        if (month >= 6 && month <= 10) {
            return "Kharif";
        } else {
            return "Rabi";
        }
    }

    @PostMapping("/predict")
    public MlResponse predictYield() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("AUTH = " + auth);
        if (auth != null) {
            System.out.println("AUTHORITIES = " + auth.getAuthorities());
        }

        User user = userService.getCurrentUser();
        Farm farm=farmRepo.findByUser(user)
                .orElseThrow(()->new RuntimeException("Farm does not exist"));
        Double latitude = farm.getLatitude();
        Double longitude = farm.getLongitude();

        SoilDataResponse soil = soilService.getSoilData(latitude, longitude);

        WeatherResponse weather = weatherService.getWeather(latitude, longitude);

        LocationResponse location = locationService.getInfo(latitude, longitude);

        MlRequest mlRequest = new MlRequest();

        mlRequest.setCropType(farm.getCrop());
        mlRequest.setSeason(getSeason());
        mlRequest.setDistrict(location.getDistrict());

        mlRequest.setIrrigationType(farm.getIrrigationType());

        mlRequest.setNitrogen(soil.getNitrogen());
        mlRequest.setPhosphorus(soil.getPhosphorus());
        mlRequest.setPotassium(soil.getPotassium());

        mlRequest.setSoilPh(Double.parseDouble(String.valueOf(farm.getPhLevel())));
        mlRequest.setSoilMoisture(soil.getSoilMoisture() * 100);

        mlRequest.setAvgTemperature(weather.getAvgTemperature());
        mlRequest.setTotalRainfall(weather.getTotalRainfall());
        mlRequest.setHumidity(weather.getHumidity());
        System.out.println(mlRequest);
        return yieldPredictionService.predict(mlRequest);
    }
}

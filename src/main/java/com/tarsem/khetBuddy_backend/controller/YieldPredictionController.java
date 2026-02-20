package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.model.User;
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

        Double latitude = user.getLatitude();
        Double longitude = user.getLongitude();

        SoilDataResponse soil = soilService.getSoilData(latitude, longitude);

        WeatherResponse weather = weatherService.getWeather(latitude, longitude);

        LocationResponse location = locationService.getInfo(latitude, longitude);

        MlRequest mlRequest = new MlRequest();

        mlRequest.setCropType(user.getCrop());
        mlRequest.setSeason(getSeason());
        mlRequest.setDistrict(location.getDistrict());

        mlRequest.setIrrigationType(user.getIrrigation_type());

        mlRequest.setNitrogen(soil.getNitrogen());
        mlRequest.setPhosphorus(soil.getPhosphorus());
        mlRequest.setPotassium(soil.getPotassium());

        mlRequest.setSoilPh(Double.parseDouble(user.getPh_level()));
        mlRequest.setSoilMoisture(soil.getSoilMoisture() * 100);

        mlRequest.setAvgTemperature(weather.getAvgTemperature());
        mlRequest.setTotalRainfall(weather.getTotalRainfall());
        mlRequest.setHumidity(weather.getHumidity());
        System.out.println(mlRequest);
        return yieldPredictionService.predict(mlRequest);
    }
}

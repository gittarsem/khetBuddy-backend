package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.LocationResponse;
import com.tarsem.khetBuddy_backend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/info")
    public LocationResponse getInfo(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) {

        return locationService.getInfo(latitude, longitude);
    }
}


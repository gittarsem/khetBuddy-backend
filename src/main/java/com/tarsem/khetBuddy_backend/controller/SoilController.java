package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.SoilDataResponse;
import com.tarsem.khetBuddy_backend.service.SoilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SoilController {

    @Autowired
    private SoilService soilService;

    @GetMapping("/soil")
    public SoilDataResponse getSoilInfo(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        return soilService.getSoilData(lat, lon);
    }
}


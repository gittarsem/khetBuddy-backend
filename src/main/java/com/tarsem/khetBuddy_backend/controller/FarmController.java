package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.FarmDetails;
import com.tarsem.khetBuddy_backend.model.Farm;

import com.tarsem.khetBuddy_backend.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/farm")
public class FarmController {
    @Autowired
    private FarmService farmService;

    @GetMapping("/my-farms")
    public List<Farm> getFarm(Authentication authentication){
        return farmService.getFarm(authentication);
    }

    @PostMapping("/add")
    public Farm addFarm(@RequestBody FarmDetails farmDetails,
                                  Authentication authentication){
        return farmService.addFarm(farmDetails,authentication);
    }

    @DeleteMapping("/delete/{farmId}")
    public String deleteFarm(@PathVariable Long farmId){
        farmService.deleteFarm(farmId);
        return "Farm deleted: "+farmId;
    }
}

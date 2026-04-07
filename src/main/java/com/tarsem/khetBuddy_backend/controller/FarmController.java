package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.farmer.FarmDetails;
import com.tarsem.khetBuddy_backend.entity.Farm;
import com.tarsem.khetBuddy_backend.service.FarmServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/farm")
@SecurityRequirement(name = "bearer")
@Tag(name = "Farm Controller", description = "APIs for managing user farms")
public class FarmController {

    @Autowired
    private FarmServiceImpl farmServiceImpl;

    @GetMapping("/my-farms")
    @Operation(
            summary = "Get user farms",
            description = "Fetches all farms associated with the authenticated user"
    )
    public List<Farm> getFarm(Authentication authentication){
        return farmServiceImpl.getFarm(authentication);
    }

    @PostMapping("/add")
    @Operation(
            summary = "Add new farm",
            description = "Creates a new farm for the authenticated user"
    )
    public Farm addFarm(@RequestBody FarmDetails farmDetails,
                        Authentication authentication){
        return farmServiceImpl.addFarm(farmDetails,authentication);
    }

    @DeleteMapping("/delete/{farmId}")
    @Operation(
            summary = "Delete farm",
            description = "Deletes a farm using its ID"
    )
    public String deleteFarm(@PathVariable Long farmId){
        farmServiceImpl.deleteFarm(farmId);
        return "Farm deleted: "+farmId;
    }
}
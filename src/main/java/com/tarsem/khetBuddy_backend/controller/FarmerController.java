package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.service.ImageService;
import com.tarsem.khetBuddy_backend.service.Interfaces.FarmerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;


@RestController
@RequestMapping("/farmer")
@Slf4j
@Tag(name = "Farmer Controller" ,description = "Farmer can update Farm and his profile")
public class FarmerController {
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/details" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Farmer updates its details")
    public ResponseEntity<FarmerUpdateProfileResponseDTO> farmerDetails(@RequestPart("data") FarmerUpdateProfileRequestDTO requestDTO,
                                                                        @RequestPart("file") MultipartFile file){
        String imageUrl=imageService.uploadImage(file);
        return ResponseEntity.ok(farmerService.farmerDetailsUpdate(requestDTO,imageUrl));
    }

    @PatchMapping(value = "details")
    @Operation(description = "Farmer updates its details")
    public ResponseEntity<FarmerUpdateProfileResponseDTO> farmerUpdateDetails(
            @RequestBody FarmerUpdateProfileRequestDTO requestDTO){
        return ResponseEntity.ok(farmerService.farmerUpdateDetailsUpdate(requestDTO));
    }

    @GetMapping("/{farmerId}/profilePic")
    public ResponseEntity<ProfilePicDTO> getProfileImage(@PathVariable Long farmerId)  {
        return ResponseEntity.ok(farmerService.getProfileImage(farmerId));
    }

    @PatchMapping("/{farmerId}/profilePic")
    public ResponseEntity<ProfilePicDTO> updateProfile(@PathVariable Long farmerId,
                                                           @RequestPart("file") MultipartFile file){
        String imageUrl=imageService.uploadImage(file);
        return ResponseEntity.ok(farmerService.updateProfilePic(farmerId,imageUrl));
    }
}

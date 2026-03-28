package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.farmer.FarmerProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.farmer.FarmerUpdateProfileRequestDTO;
import com.tarsem.khetBuddy_backend.dto.farmer.FarmerUpdateProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.farmer.ProfilePicDTO;
import com.tarsem.khetBuddy_backend.external.ImageClient;
import com.tarsem.khetBuddy_backend.service.Interfaces.FarmerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/farmer")
@Slf4j
@SecurityRequirement(name = "bearer")
@Tag(name = "Farmer Controller", description = "Farmer profile and farm management APIs")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private ImageClient imageClient;

    @PostMapping(value = "/details", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update farmer profile with image",
            description = "Updates farmer details along with profile image upload"
    )
    public ResponseEntity<FarmerUpdateProfileResponseDTO> farmerDetails(
            @RequestPart("data") FarmerUpdateProfileRequestDTO requestDTO,
            @RequestPart("file") MultipartFile file){

        String imageUrl = imageClient.uploadImage(file);
        return ResponseEntity.ok(farmerService.farmerDetailsUpdate(requestDTO, imageUrl));
    }

    @PatchMapping("/details")
    @Operation(
            summary = "Update farmer profile",
            description = "Updates farmer details without changing profile image"
    )
    public ResponseEntity<FarmerUpdateProfileResponseDTO> farmerUpdateDetails(
            @RequestBody FarmerUpdateProfileRequestDTO requestDTO){

        return ResponseEntity.ok(farmerService.farmerUpdateDetailsUpdate(requestDTO));
    }

    @GetMapping("/details")
    @Operation(
            summary = "Get farmer profile",
            description = "Fetches the details of the logged-in farmer"
    )
    public ResponseEntity<FarmerProfileResponseDTO> getFarmerDetails(){
        return ResponseEntity.ok(farmerService.getFarmerDetails());
    }

    @GetMapping("/profilePic")
    @Operation(
            summary = "Get farmer profile picture",
            description = "Retrieves profile picture of a farmer by ID"
    )
    public ResponseEntity<ProfilePicDTO> getProfileImage()  {
        return ResponseEntity.ok(farmerService.getProfileImage());
    }

    @PatchMapping("/profilePic")
    @Operation(
            summary = "Update profile picture",
            description = "Updates the profile picture of a farmer"
    )
    public ResponseEntity<ProfilePicDTO> updateProfile(
            @RequestPart("file") MultipartFile file){

        String imageUrl = imageClient.uploadImage(file);
        return ResponseEntity.ok(farmerService.updateProfilePic(imageUrl));
    }
}
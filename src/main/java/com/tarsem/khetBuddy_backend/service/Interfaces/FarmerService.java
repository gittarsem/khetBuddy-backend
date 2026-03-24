package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.FarmerProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.FarmerUpdateProfileRequestDTO;
import com.tarsem.khetBuddy_backend.dto.FarmerUpdateProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.ProfilePicDTO;
import org.jspecify.annotations.Nullable;

public interface FarmerService {
    FarmerUpdateProfileResponseDTO farmerDetailsUpdate(FarmerUpdateProfileRequestDTO requestDTO, String imageUrl);

    ProfilePicDTO getProfileImage();

    ProfilePicDTO updateProfilePic(String imageUrl);

    FarmerUpdateProfileResponseDTO farmerUpdateDetailsUpdate(FarmerUpdateProfileRequestDTO requestDTO);

    @Nullable FarmerProfileResponseDTO getFarmerDetails();
}

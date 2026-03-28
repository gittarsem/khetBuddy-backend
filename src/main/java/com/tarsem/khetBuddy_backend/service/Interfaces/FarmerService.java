package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.farmer.FarmerProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.farmer.FarmerUpdateProfileRequestDTO;
import com.tarsem.khetBuddy_backend.dto.farmer.FarmerUpdateProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.farmer.ProfilePicDTO;
import org.jspecify.annotations.Nullable;

public interface FarmerService {
    FarmerUpdateProfileResponseDTO farmerDetailsUpdate(FarmerUpdateProfileRequestDTO requestDTO, String imageUrl);

    ProfilePicDTO getProfileImage();

    ProfilePicDTO updateProfilePic(String imageUrl);

    FarmerUpdateProfileResponseDTO farmerUpdateDetailsUpdate(FarmerUpdateProfileRequestDTO requestDTO);

    @Nullable FarmerProfileResponseDTO getFarmerDetails();
}

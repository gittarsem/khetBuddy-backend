package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.FarmerUpdateProfileRequestDTO;
import com.tarsem.khetBuddy_backend.dto.FarmerUpdateProfileResponseDTO;
import com.tarsem.khetBuddy_backend.dto.ProfilePicDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.web.multipart.MultipartFile;

public interface FarmerService {
    FarmerUpdateProfileResponseDTO farmerDetailsUpdate(FarmerUpdateProfileRequestDTO requestDTO, String imageUrl);

    ProfilePicDTO getProfileImage(Long farmerId);

    ProfilePicDTO updateProfilePic(Long farmerId, String imageUrl);

    FarmerUpdateProfileResponseDTO farmerUpdateDetailsUpdate(FarmerUpdateProfileRequestDTO requestDTO);
}

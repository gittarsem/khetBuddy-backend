package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.farmer.FarmDetails;
import com.tarsem.khetBuddy_backend.entity.Farm;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FarmService {
    List<Farm> getFarm(Authentication authentication);

    Farm addFarm(FarmDetails farmDetails, Authentication authentication);

    void deleteFarm(Long farmId);
}

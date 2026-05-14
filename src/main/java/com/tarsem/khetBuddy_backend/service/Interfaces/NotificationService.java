package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.yield.YieldMlResponse;
import com.tarsem.khetBuddy_backend.dto.yield.YieldPredictionDTO;
import com.tarsem.khetBuddy_backend.entity.Farm;
import com.tarsem.khetBuddy_backend.entity.FarmerDetails;

public interface NotificationService {
    void sendWelcomeFarm(Farm farmer);
    void sendWelcomeFarmer(FarmerDetails farmer);
    void sendYield(Farm farm, YieldMlResponse response);
}

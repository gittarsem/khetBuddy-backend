package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.farmer.FarmDetails;
import com.tarsem.khetBuddy_backend.dto.LocationResponse;
import com.tarsem.khetBuddy_backend.entity.Farm;
import com.tarsem.khetBuddy_backend.entity.UserEntity;
import com.tarsem.khetBuddy_backend.repo.FarmRepo;
import com.tarsem.khetBuddy_backend.repo.UserRepo;
import com.tarsem.khetBuddy_backend.external.LocationClient;
import com.tarsem.khetBuddy_backend.service.Interfaces.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FarmRepo farmRepo;

    @Autowired
    private LocationClient locationClient;

    public List<Farm> getFarm(Authentication authentication) {
        String username= authentication.getName();
        UserEntity userEntity =userRepo.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return farmRepo.findByUserEntity(userEntity);

    }

    public Farm addFarm(FarmDetails farmDetails, Authentication authentication) {
        String username=authentication.getName();
        UserEntity userEntity = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Farm farm = new Farm();

        LocationResponse locationResponse= locationClient.getInfo(farmDetails.getLatitude()
                ,farmDetails.getLongitude());

        farm.setUserEntity(userEntity);
        farm.setLatitude(farmDetails.getLatitude());
        farm.setLongitude(farmDetails.getLongitude());
        farm.setTotalLand(farmDetails.getTotal_land());
        farm.setIrrigationType(farmDetails.getIrrigation_type());
        farm.setPhLevel(Double.parseDouble(farmDetails.getPh_level()));
        farm.setCrop(farmDetails.getCrop());
        farm.setDistrict(locationResponse.getDistrict());

        return farmRepo.save(farm);

    }

    public void deleteFarm(Long farmId) {
        Farm farm = farmRepo.findById(farmId)
                .orElseThrow(() -> new RuntimeException("Farm not found"));
        farmRepo.deleteById(farmId);
    }
}

package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.FarmDetails;
import com.tarsem.khetBuddy_backend.dto.RegisterRequest;
import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.UserEntity;
import com.tarsem.khetBuddy_backend.repo.FarmRepo;
import com.tarsem.khetBuddy_backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FarmRepo farmRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Farm updateUser(FarmDetails farmDetails, String username) {
        UserEntity userEntity =userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));
        Farm farm= new Farm();

        farm.setUserEntity(userEntity);
        farm.setLatitude(farmDetails.getLatitude());
        farm.setLongitude(farmDetails.getLongitude());
        farm.setTotalLand(farmDetails.getTotal_land());
        farm.setIrrigationType(farmDetails.getIrrigation_type());
        farm.setPhLevel(Double.parseDouble(farmDetails.getPh_level()));
        farm.setCrop(farmDetails.getCrop());


        return farmRepo.save(farm);
    }


    public UserEntity getCurrentUser() {
        String username= Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        return userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username)
                );
    }
}

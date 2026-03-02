package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.FarmDetails;
import com.tarsem.khetBuddy_backend.dto.RegisterRequest;
import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.User;
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


    public User saveUser(RegisterRequest registerRequest) {
        User user=new User();
        if(userRepo.existsByUsername(registerRequest.getUsername())){
            throw new RuntimeException("User Already Exits");
        }
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return userRepo.save(user);
    }

    public Farm updateUser(FarmDetails farmDetails, String username) {
        User user=userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));
        Farm farm= new Farm();

        farm.setUser(user);
        farm.setLatitude(farmDetails.getLatitude());
        farm.setLongitude(farmDetails.getLongitude());
        farm.setTotalLand(farmDetails.getTotal_land());
        farm.setIrrigationType(farmDetails.getIrrigation_type());
        farm.setPhLevel(Double.parseDouble(farmDetails.getPh_level()));
        farm.setCrop(farmDetails.getCrop());


        return farmRepo.save(farm);
    }


    public User getCurrentUser() {
        String username= Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        return userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username)
                );
    }
}

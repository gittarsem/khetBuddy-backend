package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.dto.ProfileUpdateRequest;
import com.tarsem.khetBuddy_backend.dto.RegisterRequest;
import com.tarsem.khetBuddy_backend.model.User;
import com.tarsem.khetBuddy_backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

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

    public User updateUser(ProfileUpdateRequest profileUpdateRequest, String username) {
        User user=userRepo.findByUsername(username);
        if(username==null){
            throw new RuntimeException("User does not exists");
        }

        user.setLocation(profileUpdateRequest.getLocation());
        user.setTotal_land(profileUpdateRequest.getTotal_land());
        user.setIrrigation_type(profileUpdateRequest.getIrrigation_type());
        user.setPh_level(profileUpdateRequest.getPh_level());
        user.setCrop(profileUpdateRequest.getCrop());

        return userRepo.save(user);
    }
}

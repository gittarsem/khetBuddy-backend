package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.model.Farm;
import com.tarsem.khetBuddy_backend.model.User;
import com.tarsem.khetBuddy_backend.service.JwtService;
import com.tarsem.khetBuddy_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class UserController {
    @Autowired
    public UserService userService;

    @Autowired
    public JwtService jwtService;

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest){
        userService.saveUser(registerRequest);
        return ResponseEntity.ok(
                new AuthResponse(
                        jwtService.generateAccessToken(registerRequest.getUsername()),
                        jwtService.generateRefreshToken(registerRequest.getUsername())
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        if(authentication.isAuthenticated()){

             return ResponseEntity.ok(
                     new AuthResponse(
                             jwtService.generateAccessToken(loginRequest.getUsername()),
                             jwtService.generateRefreshToken(loginRequest.getUsername())
                     ));
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
    }

    @PutMapping("/updateProfile")
    public Farm updateProfile(@RequestBody ProfileUpdateRequest profileUpdateRequest,
                              Principal principal){
        String username=principal.getName();
        return userService.updateUser(profileUpdateRequest,username);
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestBody RefreshRequest request
    ) {

        String refreshToken = request.getRefreshToken();
        String username = jwtService.extractUserName(refreshToken);
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

        if (!jwtService.validateToken(refreshToken, userDetails)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
        String newAccessToken =
                jwtService.generateAccessToken(username);

        return ResponseEntity.ok(
                new AuthResponse(newAccessToken, refreshToken)
        );
    }
}

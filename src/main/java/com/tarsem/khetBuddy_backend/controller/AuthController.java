package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.service.Interfaces.AuthService;
import com.tarsem.khetBuddy_backend.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(name = "Auth Controller",description = "User Authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/signUp")
    @Operation(summary = "Sign up a new user", description = "Creates a new user account.")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.addNewUser(registerRequest));
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns an JWT token.")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Generates a new access token using a refresh token.")
    public ResponseEntity<RefreshDTO> refreshToken(
            @RequestBody RefreshRequest request
    ) {
        String refreshToken = request.getRefreshToken();
        String username = jwtService.extractUserName(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.validateToken(refreshToken, userDetails)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }
       String accessToken=jwtService.generateAccessToken(username);
        return ResponseEntity.ok(new RefreshDTO(accessToken));

    }

}

package com.tarsem.khetBuddy_backend.controller;

import com.tarsem.khetBuddy_backend.dto.auth.*;
import com.tarsem.khetBuddy_backend.security.service.JwtService;
import com.tarsem.khetBuddy_backend.service.Interfaces.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@Tag(
        name = "Auth Controller",
        description = "User Authentication APIs"
)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/signUp")
    @Operation(
            summary = "Sign up a new user",
            description = "Creates a new user account"
    )
    public ResponseEntity<AuthResponse> registerUser(

            @RequestBody(
                    description = "User registration details"
            )
            @org.springframework.web.bind.annotation.RequestBody
            RegisterRequest registerRequest
    ) {

        return ResponseEntity.ok(
                authService.addNewUser(registerRequest)
        );
    }

    @PostMapping("/login")
    @Operation(
            summary = "User login",
            description = "Authenticates user and returns JWT access token"
    )
    public ResponseEntity<AuthResponse> login(

            @RequestBody(
                    description = "User login credentials"
            )
            @org.springframework.web.bind.annotation.RequestBody
            LoginRequest loginRequest
    ) {

        return ResponseEntity.ok(
                authService.login(loginRequest)
        );
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh access token",
            description = "Generates a new access token using refresh token"
    )
    public ResponseEntity<RefreshDTO> refreshToken(

            @RequestBody(
                    description = "Refresh token request"
            )
            @org.springframework.web.bind.annotation.RequestBody
            RefreshRequest request
    ) {

        String refreshToken = request.getRefreshToken();

        String username = jwtService.extractUserName(refreshToken);

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

        if (!jwtService.validateToken(refreshToken, userDetails)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null);
        }

        String accessToken =
                jwtService.generateAccessToken(username);

        return ResponseEntity.ok(
                new RefreshDTO(accessToken)
        );
    }

    @PatchMapping("/changePassword")
    @SecurityRequirement(name = "bearer")
    @Operation(
            summary = "Change password",
            description = "Updates the password of the authenticated user"
    )
    public ResponseEntity<String> updatePassword(

            @RequestBody(
                    description = "Password change request"
            )
            @org.springframework.web.bind.annotation.RequestBody
            ChangePasswordRequestDTO request
    ) {

        return ResponseEntity.ok(
                authService.updatePassword(request)
        );
    }
}
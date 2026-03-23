package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.AuthResponse;
import com.tarsem.khetBuddy_backend.dto.LoginRequest;
import com.tarsem.khetBuddy_backend.dto.RefreshRequest;
import com.tarsem.khetBuddy_backend.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    AuthResponse addNewUser(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);

}

package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.auth.AuthResponse;
import com.tarsem.khetBuddy_backend.dto.auth.ChangePasswordRequestDTO;
import com.tarsem.khetBuddy_backend.dto.auth.LoginRequest;
import com.tarsem.khetBuddy_backend.dto.auth.RegisterRequest;

public interface AuthService {
    AuthResponse addNewUser(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);

    String updatePassword(ChangePasswordRequestDTO changePasswordRequestDTO);
}

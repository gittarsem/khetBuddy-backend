package com.tarsem.khetBuddy_backend.service.Interfaces;

import com.tarsem.khetBuddy_backend.dto.*;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    AuthResponse addNewUser(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);

    String updatePassword(ChangePasswordRequestDTO changePasswordRequestDTO);
}

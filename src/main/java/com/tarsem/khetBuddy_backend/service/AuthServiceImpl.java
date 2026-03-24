package com.tarsem.khetBuddy_backend.service;

import com.tarsem.khetBuddy_backend.Exception.BadUsernamePassword;
import com.tarsem.khetBuddy_backend.dto.*;
import com.tarsem.khetBuddy_backend.model.UserEntity;
import com.tarsem.khetBuddy_backend.model.UserPrincipal;
import com.tarsem.khetBuddy_backend.repo.UserRepo;
import com.tarsem.khetBuddy_backend.service.Interfaces.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.UnableToRegisterMBeanException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    public AuthResponse generateToken(UserEntity user){
        String[] tokenString=new String[2];
        tokenString[0]=jwtService.generateAccessToken(user.getUsername());
        tokenString[1]=jwtService.generateRefreshToken(user.getUsername());

        return new AuthResponse(
                tokenString[0],tokenString[1]
        );
    }

    @Override
    public AuthResponse addNewUser(RegisterRequest registerRequest) {
        UserEntity user= userRepo.findByUsername(registerRequest.getUsername()).orElse(null);

        if(user!=null){
            throw new RuntimeException("User already exist");

        }
        UserEntity newUser=modelMapper.map(registerRequest,UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepo.save(newUser);
        return generateToken(newUser);

    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
            UserEntity user=userPrincipal.getUser();
            return generateToken(user);

        } catch (BadUsernamePassword e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    @Override
    @Transactional
    public String updatePassword(ChangePasswordRequestDTO requestDTO) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDTO.getUsername(),
                            requestDTO.getPassword()
                    )
            );

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            UserEntity user = userPrincipal.getUser();

            if (requestDTO.getNewPassword() == null || requestDTO.getNewPassword().isEmpty()
            || requestDTO.getPassword().equals(requestDTO.getNewPassword())) {
                throw new RuntimeException("New password cannot be empty or same");
            }

            user.setPassword(passwordEncoder.encode(requestDTO.getNewPassword()));

            userRepo.save(user);

            return "Password Changed Successfully";

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid current password");
        }

    }


}

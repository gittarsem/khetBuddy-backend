package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerProfileResponseDTO {

    private String firstName;
    private String lastName;
    private String phoneNo;
    private String profileImage;
    private String email;

}
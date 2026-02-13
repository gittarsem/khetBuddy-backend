package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequest {

    private String location;
    private int total_land;
    private String irrigation_type;
    private String ph_level;
    private String crop;
}

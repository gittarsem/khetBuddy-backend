package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private int id;
    private String username;

    private String location;
    private int total_land;
    private String irrigation_type;
    private String ph_level;

    private String crop;
}

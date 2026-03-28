package com.tarsem.khetBuddy_backend.dto.farmer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmDetails {


    private int total_land;
    private String irrigation_type;
    private String ph_level;
    private String crop;

    public Double latitude;
    public Double longitude;

}

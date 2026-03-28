package com.tarsem.khetBuddy_backend.dto.farmer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FarmerDayPlan {

    private int day;
    private String date;
    private String action; // Irrigate / Skip
    private double hours;
    private int cycles;
    private String note;
}

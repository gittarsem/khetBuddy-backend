package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLDayPlan {
    private int day;
    private String date;
    private boolean irrigate;
    private double irrigationMm;
    private double rainExpected;
    private String reason;
}

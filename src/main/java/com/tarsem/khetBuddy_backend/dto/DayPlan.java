package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DayPlan {

    private int day;
    private LocalDate date;

    private boolean irrigate;
    private double hours;
    private int cycles;

    private String message;
}

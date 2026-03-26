package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IrrigationMLRequestDTo {
    private String crop;
    private String stage;
    private String district;
    private Integer lastIrrigationDay;
    private Double last_week_irrigation_mm;
}

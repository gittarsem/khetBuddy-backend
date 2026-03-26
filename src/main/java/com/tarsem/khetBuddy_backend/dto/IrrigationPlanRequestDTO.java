package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IrrigationPlanRequestDTO {

    private Integer lastIrrigationDay;
    private LocalDate sowing_date;
    private String field_unit;
    private String soil_type;
    private Double daily_avg;
    private String pump_type;
}

package com.tarsem.khetBuddy_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("last_irrigated_day")
    private Integer lastIrrigationDay;
    @JsonProperty("last_week_irrigation_mm")
    private Double last_week_irrigation_mm;
}

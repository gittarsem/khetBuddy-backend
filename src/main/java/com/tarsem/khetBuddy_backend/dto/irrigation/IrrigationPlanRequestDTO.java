package com.tarsem.khetBuddy_backend.dto.irrigation;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IrrigationPlanRequestDTO {

    @Min(1)
    @Max(30)
    private Integer lastIrrigationDay;

    @NotNull
    private LocalDate sowing_date;

    @NotBlank
    private String field_unit;

    @NotBlank
    private String soil_type;

    @DecimalMin("0.0")
    @DecimalMax("12.0")
    private Double daily_avg;

    @NotBlank
    private String pump_type;
}

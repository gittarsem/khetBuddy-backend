package com.tarsem.khetBuddy_backend.dto.irrigation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IrrigationAdviceDTO {

    private boolean irrigateToday;
    private double hoursRequired;
    private int cycles;
    private String message;
}

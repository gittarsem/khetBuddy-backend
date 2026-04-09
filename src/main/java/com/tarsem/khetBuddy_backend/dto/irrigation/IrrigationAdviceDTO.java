package com.tarsem.khetBuddy_backend.dto.irrigation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IrrigationAdviceDTO {

    private boolean irrigateToday;
    private double hoursRequired;
    private int cycles;
    private String message;
}

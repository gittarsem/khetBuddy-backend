package com.tarsem.khetBuddy_backend.dto.irrigation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MLImmediateResult {
    private boolean irrigateToday;
    private boolean emergency;
    private double irrigationMm;
    private String reason;
}

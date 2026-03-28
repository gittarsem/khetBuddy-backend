package com.tarsem.khetBuddy_backend.dto.yield;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YieldPredictionDTO {
    private Long id;
    private String cropType;
    private Double yieldExpected;
    private Double yieldLower;
    private Double yieldHigher;
    private LocalDateTime createdAt;

}

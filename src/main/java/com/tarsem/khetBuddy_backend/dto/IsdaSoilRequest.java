package com.tarsem.khetBuddy_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsdaSoilRequest {
    private double lat;
    private double lon;
    private String property;
}


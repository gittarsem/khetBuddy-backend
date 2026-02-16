package com.tarsem.khetBuddy_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class RainfallHistoryResponse {

    private List<String> dates;
    private List<Double> rainfall;

    private double totalRainfall;
    private String pattern;
}

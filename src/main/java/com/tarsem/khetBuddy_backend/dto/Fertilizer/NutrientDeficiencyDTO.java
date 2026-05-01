package com.tarsem.khetBuddy_backend.dto.Fertilizer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutrientDeficiencyDTO {

    private String nitrogen;
    private String phosphorus;
    private String potassium;

}

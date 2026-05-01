package com.tarsem.khetBuddy_backend.dto.Fertilizer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tarsem.khetBuddy_backend.enums.IrrigationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FertilizerPredictionRequest {

    @JsonProperty("soil_n")
    private float nitrogen;

    @JsonProperty("soil_ph")
    private float phosphorus;

    @JsonProperty("soil_k")
    private float potassium;

    @NotNull
    private IrrigationType irrigationType;

}

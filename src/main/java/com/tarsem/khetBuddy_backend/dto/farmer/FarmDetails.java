package com.tarsem.khetBuddy_backend.dto.farmer;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmDetails {

    @Min(1)
    @Max(10000)
    private int total_land;

    @NotBlank
    private String irrigation_type;

    @DecimalMin("1.0") @DecimalMax("14.0")
    private String ph_level;

    @NotBlank
    private String crop;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    public Double latitude;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    public Double longitude;

}

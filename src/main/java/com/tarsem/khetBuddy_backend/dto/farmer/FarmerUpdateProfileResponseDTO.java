package com.tarsem.khetBuddy_backend.dto.farmer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerUpdateProfileResponseDTO {
    private String firstName;
    private String lastName;
    private String phoneNo;

}

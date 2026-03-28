package com.tarsem.khetBuddy_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="farms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Farm {

    @Id
    @GeneratedValue
    private Long id;

    private String crop;
    private String irrigationType;

    private double latitude;
    private double longitude;

    private double phLevel;
    private double totalLand;

    private String district;


    private LocalDate sowing_date;
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private UserEntity userEntity;
}

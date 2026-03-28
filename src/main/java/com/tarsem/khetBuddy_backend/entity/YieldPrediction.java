package com.tarsem.khetBuddy_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "yield_predictions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YieldPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cropType;
    private String season;

    private String district;
    private String state;
    private double latitude;
    private double longitude;

    private double nitrogen;
    private double phosphorus;
    private double potassium;
    private double soilPh;
    private double soilMoisture;

    private double yieldLower;
    private double yieldExpected;
    private double yieldHigher;

    private String unit;

    @Column(length = 1000)
    private String confidenceNote;

    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;
}

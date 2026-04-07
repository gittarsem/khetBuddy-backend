package com.tarsem.khetBuddy_backend.enums;

public enum SoilType {
    LOAMY, CLAY, SANDY;

    public static SoilType from(String value) {
        return SoilType.valueOf(value.trim().toUpperCase());
    }
}

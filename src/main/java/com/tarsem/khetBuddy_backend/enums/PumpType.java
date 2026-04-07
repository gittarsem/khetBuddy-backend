package com.tarsem.khetBuddy_backend.enums;

public enum PumpType {
    SMALL,MEDIUM,LARGE;

    public static PumpType from(String value){
        return PumpType.valueOf(value.trim().toUpperCase());
    }
}

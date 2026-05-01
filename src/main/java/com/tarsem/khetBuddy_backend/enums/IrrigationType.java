package com.tarsem.khetBuddy_backend.enums;


public enum IrrigationType {
    DRIP,Flood,Sprinkler;

    public static IrrigationType from(String value){
        return IrrigationType.valueOf(value.trim());
    }
}

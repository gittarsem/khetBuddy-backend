package com.tarsem.khetBuddy_backend.enums;

public enum FieldUnit {
    ACRE,HECTARE,M2;

    public static FieldUnit from(String value){
        return FieldUnit.valueOf(value.trim().toUpperCase());
    }
}

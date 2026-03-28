package com.tarsem.khetBuddy_backend.exception;

public class UnAuthorisedException extends RuntimeException {
    public UnAuthorisedException(String farmerDoesNotHaveAccess) {
        super(farmerDoesNotHaveAccess);
    }
}

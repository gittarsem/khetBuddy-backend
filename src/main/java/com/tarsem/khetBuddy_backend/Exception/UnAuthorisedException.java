package com.tarsem.khetBuddy_backend.Exception;

public class UnAuthorisedException extends RuntimeException {
    public UnAuthorisedException(String farmerDoesNotHaveAccess) {
        super(farmerDoesNotHaveAccess);
    }
}

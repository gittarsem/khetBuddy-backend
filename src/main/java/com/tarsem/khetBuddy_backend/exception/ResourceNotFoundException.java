package com.tarsem.khetBuddy_backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String farmerDoesNotExist) {
    super(farmerDoesNotExist);
    }
}

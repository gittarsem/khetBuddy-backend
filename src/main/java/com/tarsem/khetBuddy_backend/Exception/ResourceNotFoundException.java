package com.tarsem.khetBuddy_backend.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String farmerDoesNotExist) {
    super(farmerDoesNotExist);
    }
}

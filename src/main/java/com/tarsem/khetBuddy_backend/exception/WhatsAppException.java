package com.tarsem.khetBuddy_backend.exception;


public class WhatsAppException extends RuntimeException {

    private final String errorCode;
    private final int statusCode;

    // Basic constructor
    public WhatsAppException(String message) {
        super(message);
        this.errorCode = null;
        this.statusCode = 0;
    }

    // With cause (for debugging root issue)
    public WhatsAppException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
        this.statusCode = 0;
    }

    // With error code (Meta API errors)
    public WhatsAppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = 0;
    }

    // Full constructor (recommended for API integration)
    public WhatsAppException(String message, String errorCode, int statusCode) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

package com.tarsem.khetBuddy_backend.exception;

public class BadUsernamePassword extends RuntimeException{
    public BadUsernamePassword(String msg){
        super(msg);
    }
}

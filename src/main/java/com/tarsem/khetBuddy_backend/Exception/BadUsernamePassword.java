package com.tarsem.khetBuddy_backend.Exception;

public class BadUsernamePassword extends RuntimeException{
    public BadUsernamePassword(String msg){
        super(msg);
    }
}

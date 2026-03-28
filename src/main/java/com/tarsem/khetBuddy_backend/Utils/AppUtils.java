package com.tarsem.khetBuddy_backend.Utils;


import com.tarsem.khetBuddy_backend.entity.UserEntity;
import com.tarsem.khetBuddy_backend.entity.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {

    public static UserEntity giveMeCurrentUser(){
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity user=userPrincipal.getUser();
        if(user==null) throw new NullPointerException("User does not exist");
        return userPrincipal.getUser();


    }
}

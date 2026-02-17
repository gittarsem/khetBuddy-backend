package com.tarsem.khetBuddy_backend.service;


import com.tarsem.khetBuddy_backend.model.User;
import com.tarsem.khetBuddy_backend.model.UserPrincipal;
import com.tarsem.khetBuddy_backend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));

        if(user==null){
            System.out.println("Login attempt for: " + username);
            throw new UsernameNotFoundException("User 404");
        }

        return new UserPrincipal(user);
    }
}

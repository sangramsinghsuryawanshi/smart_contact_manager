package com.scm.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositries.UserRepo;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    public SecurityCustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUserEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email :" + username));
    }

}

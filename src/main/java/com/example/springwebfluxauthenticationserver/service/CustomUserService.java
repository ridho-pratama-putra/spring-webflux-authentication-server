package com.example.springwebfluxauthenticationserver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springwebfluxauthenticationserver.models.CustomUser;
import com.example.springwebfluxauthenticationserver.repository.CustomUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserService implements UserDetailsService{

    private final CustomUserRepository customUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser appUser = customUserRepository.findByUsername(username).orElse(null);

        if(appUser == null) {
            throw new UsernameNotFoundException("user tidak ditemukan");
        }

        return appUser;
    }
    
}

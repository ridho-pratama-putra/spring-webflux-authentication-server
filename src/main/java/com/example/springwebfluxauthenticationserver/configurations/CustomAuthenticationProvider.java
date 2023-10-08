package com.example.springwebfluxauthenticationserver.configurations;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.springwebfluxauthenticationserver.models.CustomUser;
import com.example.springwebfluxauthenticationserver.models.JwtAuthenticationToken;
import com.example.springwebfluxauthenticationserver.repository.CustomUserRepository;
import com.example.springwebfluxauthenticationserver.repository.CustomUserTokenRepository;
import com.example.springwebfluxauthenticationserver.service.JwtService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
    Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Value("${enable.custom.authentication.provider}")
    private boolean isEnableCustomAuthenticationProvider;

    private final JwtService jwtService;
    private final CustomUserTokenRepository customUserTokenRepository;
    private final CustomUserRepository customUserRepository;

    public CustomAuthenticationProvider(JwtService jwtService, CustomUserTokenRepository customUserTokenRepository, CustomUserRepository customUserRepository) {
        this.jwtService = jwtService;
        this.customUserTokenRepository = customUserTokenRepository;
        this.customUserRepository = customUserRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        String username = (String) authentication.getPrincipal();

        CustomUser findByUsername = customUserRepository.findByUsername(username).orElse(null);
        String generateToken = "";
        String generateRefreshToken = "";
        try {
            generateToken = jwtService.generateToken(findByUsername);
            generateRefreshToken = jwtService.generateRefreshToken(findByUsername);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(null, generateToken, generateRefreshToken, username, null);
        logger.info("authenticate : CustomAuthenticationProvider :: {}", jwtAuthenticationToken.getPrincipal());
        return jwtAuthenticationToken;
    
    }

    @Override
    public boolean supports(Class<?> authentication) {
        logger.info("supports : CustomAuthenticationProvider");
        return isEnableCustomAuthenticationProvider;
    }
    
}

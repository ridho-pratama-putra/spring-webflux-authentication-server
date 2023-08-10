package com.example.springwebfluxauthenticationserver.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationManager implements ReactiveAuthenticationManager{

    private final CustomAuthenticationProvider customAuthenticationProvider;
    Logger logger = LoggerFactory.getLogger(CustomAuthenticationManager.class);
    
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        ProviderManager providerManager = new ProviderManager(customAuthenticationProvider);
        Authentication authenticate = providerManager.authenticate(authentication);
        
        return Mono.just(authenticate);
    }
}

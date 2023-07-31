package com.example.springwebfluxauthenticationserver.configurations;

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
    
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        ProviderManager providerManager = new ProviderManager(customAuthenticationProvider);
        Authentication authenticate = providerManager.authenticate(authentication);
        
        return Mono.just(authenticate);
    }
}

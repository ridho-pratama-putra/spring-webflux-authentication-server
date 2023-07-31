package com.example.springwebfluxauthenticationserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import com.example.springwebfluxauthenticationserver.configurations.CustomAuthenticationConverter;

import reactor.core.publisher.Mono;

@Configuration
public class JWTFilter extends AuthenticationWebFilter {
    Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    public JWTFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        super.setServerAuthenticationConverter(new CustomAuthenticationConverter());
    }

    @Override
    protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
        logger.info("masuk JWT onAuthenticationSuccess filter");
        return super.onAuthenticationSuccess(authentication, webFilterExchange);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.info("masuk JWT filter");
        return super.filter(exchange, chain);
    }
}

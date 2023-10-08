package com.example.springwebfluxauthenticationserver.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
        logger.info("onAuthenticationFailure :: {}", exception.getMessage());
        return Mono.error(exception);
    }

}

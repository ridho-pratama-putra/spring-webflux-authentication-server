package com.example.springwebfluxauthenticationserver.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.springwebfluxauthenticationserver.models.CustomBearerToken;

import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationConverter implements ServerAuthenticationConverter {
        Logger logger = LoggerFactory.getLogger(CustomAuthenticationConverter.class);

        @Override
        public Mono<Authentication> convert(ServerWebExchange exchange) {
                logger.info("convert : CustomAuthenticationConverter");
                Mono<Authentication> justOrEmpty = Mono.justOrEmpty(
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION))
                        .filter(s -> s.startsWith("Bearer "))
                        .map(s -> s.substring(7))
                        .map(s -> new CustomBearerToken(null, null, null, null, null))
                ;
                return justOrEmpty;

        }
}

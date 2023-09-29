package com.example.springwebfluxauthenticationserver.configurations;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.springwebfluxauthenticationserver.models.CustomBearerToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        logger.info("onAuthenticationSuccess :: {}", authentication.getPrincipal());

        if (webFilterExchange.getExchange().getRequest().getPath().toString().equals("/login")) {
            SimpleGrantedAuthority sampleAuth = new SimpleGrantedAuthority("ROLE_APA_INI_YA");
            CustomBearerToken customBearerToken = new CustomBearerToken(Collections.singleton(sampleAuth), null, null, "bang sardi", null);
            ObjectMapper objectMapper = new ObjectMapper();
            Flux<DataBuffer> just = null;
            try {
                just = Flux.just(webFilterExchange.getExchange().getResponse().bufferFactory().wrap(objectMapper.writeValueAsString(customBearerToken).getBytes()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            webFilterExchange.getExchange().getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return webFilterExchange.getExchange().getResponse().writeAndFlushWith(Flux.just(just));
        }

        return webFilterExchange.getChain().filter(webFilterExchange.getExchange());
    }    
}

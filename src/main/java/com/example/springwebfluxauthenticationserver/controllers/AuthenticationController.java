package com.example.springwebfluxauthenticationserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springwebfluxauthenticationserver.models.CustomAuthenticationRequestBody;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import reactor.core.publisher.Mono;

@RestController
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    // @PostMapping("/login")
    // public Mono<ResponseEntity<String>> ghf(@RequestBody CustomAuthenticationRequestBody req) {
    //     logger.info("/login ::: ");
    //     return  Mono.just(ResponseEntity.ok().body("selamat"));
    // }

    @DeleteMapping("/logout")
    public Mono<ResponseEntity<String>> ghskf(@RequestBody CustomAuthenticationRequestBody req) {
        logger.info("/logout ::: ");
        return  Mono.just(ResponseEntity.ok().body("wilujeng"));
    }
}

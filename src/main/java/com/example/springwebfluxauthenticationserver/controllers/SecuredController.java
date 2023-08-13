package com.example.springwebfluxauthenticationserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/secured-controller")
public class SecuredController {
    
    @GetMapping("")
    Mono<String> hai() {
        return Mono.just("hai you're allowed");
    }
}

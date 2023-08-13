package com.example.springwebfluxauthenticationserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/dummy-controller")
public class DummyController {
    
    @GetMapping("/")
    public Mono<String> dummy() {
        return Mono.just("hallo");
    }

    @GetMapping("/boom")
    public Mono<String> booom() {
        return Mono.just("booom");
    }
}

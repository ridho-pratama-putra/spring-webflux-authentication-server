package com.example.springwebfluxauthenticationserver.configurations;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.springwebfluxauthenticationserver.models.JwtAuthenticationToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationConverter implements ServerAuthenticationConverter {
        Logger logger = LoggerFactory.getLogger(CustomAuthenticationConverter.class);

        @Autowired 
        private ObjectMapper objectMapper;
        
        @Override
        public Mono<Authentication> convert(ServerWebExchange exchange) {
                logger.info("convert : CustomAuthenticationConverter {} ", exchange.getRequest().getURI().getPath());
                if (exchange.getRequest().getURI().getPath().equals("/login")) {
                        Flux<Authentication> flatMap = exchange.getRequest().getBody()
                                .flatMap(dataBuffer -> {
                                        String jsonBody;
                                        JsonNode rootNode;
                                        try {
                                                jsonBody = new String(dataBuffer.asInputStream().readAllBytes());
                                                rootNode = objectMapper.readTree(jsonBody);
                                                String username = rootNode.get("username").asText();
                                                String password = rootNode.get("password").asText();
                            
                                                Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
                                                return Mono.just(authentication);
                                        } catch (JsonMappingException e) {
                                                e.printStackTrace();
                                        } catch (JsonProcessingException e) {
                                                e.printStackTrace();
                                        } catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                        return Mono.empty();
                                });
                                
                        return flatMap.last();
                }
                Mono<Authentication> justOrEmpty = Mono.justOrEmpty(
                        exchange.getRequest()
                                .getHeaders()
                                .getFirst(HttpHeaders.AUTHORIZATION))
                                .filter(s -> s.startsWith("Bearer "))
                                .map(s -> s.substring(7))
                                .map(s -> {
                                        logger.info("token val ::: ", s);
                                        return new JwtAuthenticationToken(null, s, null, "Bangkeeeee", null);
                                })
                ;
                return justOrEmpty;
        }
}

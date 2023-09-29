package com.example.springwebfluxauthenticationserver.configurations;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.springwebfluxauthenticationserver.models.CustomAuthenticationRequestBody;
import com.example.springwebfluxauthenticationserver.models.CustomBearerToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationConverter implements ServerAuthenticationConverter {
        Logger logger = LoggerFactory.getLogger(CustomAuthenticationConverter.class);

        @Override
        public Mono<Authentication> convert(ServerWebExchange exchange) {
                logger.info("convert : CustomAuthenticationConverter {} ", exchange.getRequest().getURI().getPath());
                if (exchange.getRequest().getURI().getPath().equals("/login")) {
                        Authentication authentication = new Authentication() {

                                @Override
                                public String getName() {
                                        return "SAM KOO";
                                }

                                @Override
                                public Collection<? extends GrantedAuthority> getAuthorities() {
                                        // TODO Auto-generated method stub
                                        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
                                }

                                @Override
                                public Object getCredentials() {
                                        // TODO Auto-generated method stub
                                        throw new UnsupportedOperationException("Unimplemented method 'getCredentials'");
                                }

                                @Override
                                public Object getDetails() {
                                        // TODO Auto-generated method stub
                                        // throw new UnsupportedOperationException("Unimplemented method 'getDetails'");
                                        return null;
                                }

                                @Override
                                public Object getPrincipal() {
                                        // TODO Auto-generated method stub
                                        throw new UnsupportedOperationException("Unimplemented method 'getPrincipal'");
                                }

                                @Override
                                public boolean isAuthenticated() {
                                        // TODO Auto-generated method stub
                                        throw new UnsupportedOperationException("Unimplemented method 'isAuthenticated'");
                                }

                                @Override
                                public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                                        // TODO Auto-generated method stub
                                        throw new UnsupportedOperationException("Unimplemented method 'setAuthenticated'");
                                }
                        };
                        return Mono.just(authentication);
                }
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

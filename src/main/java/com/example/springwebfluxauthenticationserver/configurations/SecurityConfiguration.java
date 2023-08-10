package com.example.springwebfluxauthenticationserver.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    // private final JWTFilter jwtFilter;

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) throws Exception {
        // httpSecurity
			// .authorizeExchange(customizer -> customizer
            //     .pathMatchers("/dummy-controller/**").permitAll()
            //     .pathMatchers("/login").permitAll()
            //     .pathMatchers("/v3/api-docs/*", "/v3/api-docs", "/webjars/swagger-ui/*").permitAll()
            //     .anyExchange().authenticated()
            // )
            // .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            // .formLogin(withDefaults())
        // ;

        return httpSecurity.build();
    }
}

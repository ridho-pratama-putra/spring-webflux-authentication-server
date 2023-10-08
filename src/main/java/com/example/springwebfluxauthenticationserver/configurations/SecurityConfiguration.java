package com.example.springwebfluxauthenticationserver.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final CustomAuthenticationManager customAuthenticationManager;
    private final CustomAuthenticationConverter customAuthenticationConverter;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public AuthenticationWebFilter authenticationWebFilter() {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(customAuthenticationManager);
        filter.setServerAuthenticationConverter(customAuthenticationConverter);

        // additionally specify, not necessary
        // filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/login", "/dummy-controller/**", "/logout"));
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        return filter;
    }

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(customizer -> customizer.disable())
            .authorizeExchange(customizer -> customizer
                // .pathMatchers("/login").permitAll()
                .pathMatchers("/v3/api-docs/*", "/v3/api-docs", "/webjars/swagger-ui/*").permitAll()
                .anyExchange().authenticated()
            )
            .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .formLogin(customizer -> customizer.disable())
            ;

        return httpSecurity.build();
    }
}

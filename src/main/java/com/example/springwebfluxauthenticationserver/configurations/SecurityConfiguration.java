package com.example.springwebfluxauthenticationserver.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import com.example.springwebfluxauthenticationserver.filter.CustomAuthenticationWebFilter;
// import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final CustomAuthenticationConverter customAuthenticationConverter;

    @Bean
    public AuthenticationWebFilter authenticationWebFilter() {
        AuthenticationWebFilter filter = new CustomAuthenticationWebFilter(customAuthenticationManager, customAuthenticationConverter);
        return filter;
    }

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(customizer -> customizer.disable())
            .authorizeExchange(customizer -> customizer
                .pathMatchers("/dummy-controller/**").permitAll()
                .pathMatchers("/v3/api-docs/*", "/v3/api-docs", "/webjars/swagger-ui/*").permitAll()
                .anyExchange().authenticated()
            )
            .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .formLogin(customizer -> customizer.disable())
            ;

        return httpSecurity.build();
    }
}

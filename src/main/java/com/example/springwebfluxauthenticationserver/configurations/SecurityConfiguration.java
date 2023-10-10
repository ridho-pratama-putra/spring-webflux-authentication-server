package com.example.springwebfluxauthenticationserver.configurations;

import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final String PUBLIC_KEY_PATH = "keys/public.pem";
    private final CustomAuthenticationManager customAuthenticationManager;
    private final CustomAuthenticationConverter customAuthenticationConverter;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final DataSource dataSource;

    @Bean
    AuthenticationWebFilter authenticationWebFilter() {
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
        OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource());
        httpSecurity
            .csrf(customizer -> customizer.disable())
            .authorizeExchange(customizer -> customizer
                // .pathMatchers("/login").permitAll()
                .pathMatchers("/v3/api-docs/*", "/v3/api-docs", "/webjars/swagger-ui/*", "/.well-known/*", "/oauth2/jwks").permitAll()
                .anyExchange().authenticated()
            )
            .addFilterAt(authenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .formLogin(customizer -> customizer.disable())
            .oauth2ResourceServer(resourceServer -> {
                resourceServer.jwt(Customizer.withDefaults());
            });

        return httpSecurity.build();
    }

    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    ReactiveJwtDecoder jwtDecoder() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        // KeyFactory factory = KeyFactory.getInstance("RSA");
        // ClassPathResource publicKeyResource = new ClassPathResource(PUBLIC_KEY_PATH);

        // disini kayanya yg kurang proper, jadi bikin invalid cert
        // InputStream inputStream = publicKeyResource.getInputStream();

        // byte[] content = inputStream.readAllBytes();
        // X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
        // PublicKey generatePublic = factory.generatePublic(pubKeySpec);

        // copy from jwtservice
        KeyFactory factory = KeyFactory.getInstance("RSA");
        ClassPathResource publicKeyResource = new ClassPathResource(PUBLIC_KEY_PATH);
        try (FileReader keyReader = new FileReader(publicKeyResource.getFile());
        PemReader pemReader = new PemReader(keyReader)) {

            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
            PublicKey generatePublic = factory.generatePublic(pubKeySpec);
            return NimbusReactiveJwtDecoder.withPublicKey((RSAPublicKey) generatePublic).build();
        }
	}

    @Bean
    JWKSource<SecurityContext> jwkSource() {
        return new JWKSource<SecurityContext>() {

            @Override
            public List<JWK> get(JWKSelector arg0, SecurityContext arg1) throws KeySourceException {
                
                try {
                    ClassPathResource publicKeyResource = new ClassPathResource(PUBLIC_KEY_PATH);
                    FileReader keyReader;
                    PemObject pemObject;
                    keyReader = new FileReader(publicKeyResource.getFile());
                    PemReader pemReader = new PemReader(keyReader);
                    pemObject = pemReader.readPemObject();
                    byte[] content = pemObject.getContent();
                    JWK parse = JWK.parse(new String(content));
                    List<JWK> result = Arrays.asList(parse);
                    return result;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }            
        };
    }
}

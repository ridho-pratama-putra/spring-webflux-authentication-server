package com.example.springwebfluxauthenticationserver.models;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomBearerToken extends AbstractAuthenticationToken {
    private final String token;
    private final String refreshToken;
    private final String username;

    public CustomBearerToken(Collection<? extends GrantedAuthority> authorities, String token, String refreshToken, String username, UserDetails appUser) {
        super(authorities);
        this.token = token;
        this.refreshToken = refreshToken;
        this.username = username;
        super.setDetails(appUser);
    }

    @Override
    public String getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return this.username;
    }
}
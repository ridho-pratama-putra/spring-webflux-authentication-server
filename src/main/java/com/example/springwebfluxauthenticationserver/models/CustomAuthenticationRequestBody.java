package com.example.springwebfluxauthenticationserver.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomAuthenticationRequestBody {

    String username;
    String password;
}

package com.example.springwebfluxauthenticationserver.models;

import java.sql.Date;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Entity(name = "app_user_token")
@NoArgsConstructor
public class CustomUserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column
    private Date lastActivity;

    @Column(name = "USER_NAME", length = 30, nullable = false)
    private String userName;

    @Column(name = "FAILED_LOGIN_COUNTER")
    private Integer failedLoginCounter;

    @Column(name = "IS_LOCK", precision = 1, nullable = false)
    private Boolean lock;

    @Column
    private String token;

    @Column
    private String refreshToken;

    @Column
    private Boolean expired;

    @Column
    private Boolean revoked;

    @Column
    private Date lastLogInDate;

    @Convert(converter = HashMapConverter.class)
    @Column(columnDefinition = "mediumtext")
    private Map<String, Object> additionalInformation;

    @Column
    private String name;

    @Column
    private String tokenNotif;
} 
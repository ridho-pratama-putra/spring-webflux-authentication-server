package com.example.springwebfluxauthenticationserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springwebfluxauthenticationserver.models.CustomUserToken;

@Repository
public interface CustomUserTokenRepository extends JpaRepository<CustomUserToken, Long>{
    Optional<CustomUserToken> findOneByUserNameAndRevokedIsFalse(String name);
}

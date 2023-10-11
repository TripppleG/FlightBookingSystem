package com.flightbookingsystem.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private Long expirationMs;
}

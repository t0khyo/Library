package com.t0khyo.library.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.PrivateKey;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "jwt.config")
public record JwtConfigProperties(
        String issuer,
        PublicKey publicKey,
        PrivateKey privateKey,
        Long tokenExpirationSeconds
) {
}

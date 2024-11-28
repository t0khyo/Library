package com.t0khyo.library.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "jwt.config")
public record JwtConfigProperties(
        String issuer,
        RSAPublicKey publicKey,
        RSAPrivateKey privateKey,
        Long tokenExpirationSeconds
) {
}

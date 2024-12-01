package com.t0khyo.library.security.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.t0khyo.library.exception.InvalidSignatureException;
import com.t0khyo.library.exception.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtConfigProperties jwtConfig;

    // generate token
    public String generateToken(Authentication authentication) throws JOSEException {
        Instant now = Instant.now();
        Instant expirationTime = now.plus(jwtConfig.tokenExpirationSeconds(), ChronoUnit.SECONDS);

        final String username = authentication.getName();
        final List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(username)
                .claim("roles", roles)
                .issuer(jwtConfig.issuer())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expirationTime))
                .jwtID(UUID.randomUUID().toString())
                .build();

        JWSSigner signer = new RSASSASigner(jwtConfig.privateKey());

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).type(JOSEObjectType.JWT).build(),
                claims
        );

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    // validate token
    public SignedJWT validateToken(String token) throws ParseException, JOSEException, TokenExpiredException, InvalidSignatureException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(jwtConfig.publicKey());

        if (!signedJWT.verify(verifier)) {
            log.error("Invalid token signature for JWT ID: {}", extractClaims(signedJWT).getJWTID());
            throw new InvalidSignatureException();
        }

        if (isTokenExpired(signedJWT)) {
            throw new TokenExpiredException();
        }

        return signedJWT;
    }

    // extract claims
    public JWTClaimsSet extractClaims(SignedJWT signedJWT) throws ParseException {
        return signedJWT.getJWTClaimsSet();
    }

    public String extractUsername(SignedJWT signedJWT) throws ParseException {
        return extractClaims(signedJWT).getSubject();
    }

    public List<String> extractRoles(SignedJWT signedJWT) throws ParseException {
        return extractClaims(signedJWT).getStringListClaim("roles");
    }

    public Date extractExpiration(SignedJWT signedJWT) throws ParseException {
        return extractClaims(signedJWT).getExpirationTime();
    }

    public boolean isTokenExpired(SignedJWT signedJWT) throws ParseException {
        return extractExpiration(signedJWT).before(new Date());
    }
}

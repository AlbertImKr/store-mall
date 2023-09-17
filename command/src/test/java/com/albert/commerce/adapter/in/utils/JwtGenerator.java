package com.albert.commerce.adapter.in.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Collections;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    private final RSAKey rsaKey;

    public JwtGenerator() throws JOSEException {
        RSAKeyGenerator RSAKeyGenerator = new RSAKeyGenerator(2048);
        this.rsaKey = RSAKeyGenerator.generate();
        KeyPair keyPair = rsaKey.toKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    public String generateToken(String subject) {

        JWKSet jwkSet = new JWKSet(rsaKey);
        NimbusJwtEncoder nimbusJwtEncoder = new NimbusJwtEncoder(new ImmutableJWKSet<>(jwkSet));
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwtClaimsSet.builder()
                        .claims(claims -> {
                                    Instant now = Instant.now();
                                    claims.put("sub", subject);
                                    claims.put("iss", "http://localhost:8080/issur");
                                    claims.put("aud", "test-client");
                                    claims.put("email", "test@email.com");
                                    claims.put("iat", now.getEpochSecond());
                                    claims.put("exp", now.plusSeconds(3600).getEpochSecond());
                                    claims.put("authenticated", true);
                                    claims.put("roles", Collections.singletonList("ROLE_USER"));
                                }
                        )
                        .build());
        Jwt jwt = nimbusJwtEncoder.encode(jwtEncoderParameters);
        return jwt.getTokenValue();
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public String generateToken(String subject, String nonce) {
        JWKSet jwkSet = new JWKSet(rsaKey);
        NimbusJwtEncoder nimbusJwtEncoder = new NimbusJwtEncoder(new ImmutableJWKSet<>(jwkSet));
        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                JwtClaimsSet.builder()
                        .claims(claims -> {
                                    Instant now = Instant.now();
                                    claims.put("sub", subject);
                                    claims.put("iss", "http://localhost:8080/issur");
                                    claims.put("aud", "test-client");
                                    claims.put("email", "test@email.com");
                                    claims.put("iat", now.getEpochSecond());
                                    claims.put("nonce", nonce);
                                    claims.put("exp", now.plusSeconds(3600).getEpochSecond());
                                }
                        )
                        .build());
        Jwt jwt = nimbusJwtEncoder.encode(jwtEncoderParameters);
        return jwt.getTokenValue();
    }
}

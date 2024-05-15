package com.melite.apigateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JWTUtil {
    public final static String PERMITTED_URL = "/login";

    @Value("${jwt.secret}")
    private String secret;

    public Claims getALlClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token ) {
        return this.getALlClaims(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

    public String generateToken(String userName) {
        return Jwts.builder()
                .claim("id", userName)
                .setSubject("Test Token")
                .setIssuedAt(java.sql.Date.from(Instant.now()))
                .setExpiration(java.sql.Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }
}

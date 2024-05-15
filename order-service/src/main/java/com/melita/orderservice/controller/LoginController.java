package com.melita.orderservice.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    @Value("${jwt.secret}")
    private String secret;

    @PostMapping
    public Map login(String userName) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Jwts.builder()
                .claim("id", userName)
                .setSubject("Test Token")
                .setIssuedAt(java.sql.Date.from(Instant.now()))
                .setExpiration(java.sql.Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, secret).compact());

        return map;
    }

}

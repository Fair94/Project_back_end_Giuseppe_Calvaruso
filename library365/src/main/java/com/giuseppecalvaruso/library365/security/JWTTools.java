package com.giuseppecalvaruso.library365.security;


import com.giuseppecalvaruso.library365.entities.Role;
import com.giuseppecalvaruso.library365.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTTools {

    private  String secret;

    public JWTTools(@Value("${jwt.secret}")String secret  ) {
        this.secret = secret;
    }

    public String createToken(User user){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .subject(String.valueOf(user.getId()))
                .claim("roles", user.getRoles().stream().map(Role::getName).toList())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    public void verifyToken(String accessToken){
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(accessToken);
    }

    public UUID getIDfromToken(String accessToken){
        return UUID.fromString(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getSubject());
    }


}

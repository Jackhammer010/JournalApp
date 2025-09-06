package com.practice.JournalApp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secret;
    @Value("${jwt.expiration.minutes}")
    private int minutes;

    public String generateToken(String username){
        Map<String, String> claims = new HashMap<>();
        return createToken(claims, username);
    }
    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }
    public boolean validateToken(String token, String username){
        String usernameFromToken = extractUsername(token);
        return usernameFromToken.equals(username) && !isTokenExpired(token);
    }
    public Long getExpirationInMillis(String token){
        Date expiration = getTokenExpiration(token);
        return expiration.getTime() - System.currentTimeMillis();
    }
    private String createToken(Map<String, String> claims, String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .id(UUID.randomUUID().toString())
                .header().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (1000L * 60 * minutes)))
                .signWith(getSigningKey())
                .compact();
    }
    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
    private boolean isTokenExpired(String token){
        return getTokenExpiration(token).before(new Date());
    }
    private Date getTokenExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

}

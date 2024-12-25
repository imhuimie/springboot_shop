package com.example.shop.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String secret = "39c7d47a9981478298da7e1648b4b4e3"; // 私钥，用于签名JWT

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1小时过期
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    public String getSubject(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, String subject) {
        return (subject.equals(getSubject(token)) && !isExpired(token));
    }

    public boolean isExpired(String token) {
        try {
            Date expiration = getExpiration(token);
            return expiration.before(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            // 已过期
            return true;
        }
    }

    public boolean validate(String token) {
        return true;
    }

    public Claims getClaimsFromHeader(String authorization) {
        return getClaims(authorization.substring(7));
    }
}
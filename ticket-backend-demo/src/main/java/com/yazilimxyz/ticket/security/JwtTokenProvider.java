package com.yazilimxyz.ticket.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationDate;

    // 1. Token Oluşturma Metodu
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Yardımcı metod: Secret key'i Key nesnesine çevirir
    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // 2. Token'dan Username (Email) Çekme Metodu
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 3. Token Geçerli mi? Kontrol Metodu
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Geçersiz JWT token");
        } catch (ExpiredJwtException e) {
            System.out.println("Süresi dolmuş JWT token");
        } catch (UnsupportedJwtException e) {
            System.out.println("Desteklenmeyen JWT token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string boş");
        }
        return false;
    }
}
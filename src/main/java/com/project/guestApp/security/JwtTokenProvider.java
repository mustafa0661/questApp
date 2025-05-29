package com.project.guestApp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key APP_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${questapp.expires.in}")
    private long EXPIRES_IN;

    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(APP_SECRET, SignatureAlgorithm.HS512)
                .compact();
    }

    Long getUserIdFromJwt(String token) {
    try {
        Claims claims = Jwts.parser()
            .setSigningKey(APP_SECRET)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return Long.parseLong(claims.getSubject());
    } catch (Exception e) {
        // Log hata
        // Örneğin: TokenException fırlatabilir veya null dönebilirsiniz
        return null;
    }
}
}
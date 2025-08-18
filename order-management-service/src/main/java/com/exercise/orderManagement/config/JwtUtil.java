package com.exercise.orderManagement.config;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
    private String secret;
	
	 private Key getSigningKey() {
	        return Keys.hmacShaKeyFor(secret.getBytes());
	    }

	    public boolean validateToken(String token) {
	        try {
	            Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token);
	            return true;
	        } catch (JwtException e) {
	            return false;
	        }
	    }

	    public String extractEmail(String token) {
	        return extractAllClaims(token).getSubject();
	    }

	    public Long extractCustomerId(String token) {
	        return ((Number) extractAllClaims(token).get("customerId")).longValue();
	    }

	    public String extractRole(String token) {
	        return extractAllClaims(token).get("role", String.class);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }
	}
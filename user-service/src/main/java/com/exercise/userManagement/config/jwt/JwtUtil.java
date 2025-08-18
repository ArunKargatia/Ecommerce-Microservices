package com.exercise.userManagement.config.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
    private String secret;
	
	private final long expiration_time = 1000 * 60 * 60 * 10;
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public String generateToken(Long customerId, String email, String role) {
		return Jwts.builder()
				.setSubject(email)
				.claim("customerId", customerId)
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration_time))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	 public boolean validateToken(String token) {
	        try {
	            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
	            return true;
	        } catch (JwtException e) {
	            return false;
	        }
	 }
	 
	 public String extractEmail(String token) {
		 return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
				 .parseClaimsJws(token).getBody().getSubject();
	 }
	 
	 public Long extractCustomerId(String token) {
		 return ((Number) Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
				 .parseClaimsJws(token).getBody().get("customerId")).longValue();
	 }
	 
	 public String extractRole(String token) {
		 return (String) Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
				 .parseClaimsJws(token).getBody().get("role");
	 }
	 
	 public boolean isTokenValid(String token, UserDetails userDetails) {
		    final String email = extractEmail(token);
		    return (email.equals(userDetails.getUsername()) && validateToken(token));
		}
	
}

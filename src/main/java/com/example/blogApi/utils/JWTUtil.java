package com.example.blogApi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {
    private static final String SECRET = System.getenv("SECRET"); // Keep the secret in a constant

    private static Key getSigningKey() {
        if (SECRET == null || SECRET.isEmpty()) { // Check if the secret is null or empty
            throw new IllegalArgumentException("No secret key.");
        }
        byte[] secretBytes = Decoders.BASE64.decode(SECRET); // Decode the secret key
        return Keys.hmacShaKeyFor(secretBytes); // Return the secret key
    }

    private Claims extractAllClaims(String token) { // Extract all claims from the token
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Use the signing key
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the body of the token in the form of claims. claims are key-value pairs
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) { // Extract claims from the token

        // try to make it that claims are not extracted if token is expired //

        final Claims claims = extractAllClaims(token); // Extract all claims from the token
        return claimsResolver.apply(claims); // Return the claims as a function of the claims resolver
    }

    public String extractUsername(String token) { // Extract the username from the token
        return extractClaims(token, Claims::getSubject); // Return the subject of the claims
    }

    private Date extractExpiration(String token) { // Extract the expiration date from the token
        return extractClaims(token, Claims::getExpiration); // Return the expiration date of the claims
    }

    private Boolean isTokenExpired(String token) { // Check if the token is expired
        return extractExpiration(token).before(new Date()); // Return true if the expiration date is before the current date
    }

    public Boolean isTokenValid(String token) { // Check if the token is valid
        try {
            final String username = extractUsername(token); // Extract the username from the token
            return (username != null && !isTokenExpired(token)); // Return true if the username is not null and the token is not expired
        } catch (Exception e) {
            return false; // Return false if there is an exception
        }
    }

    public String generateToken(Map<String, Object> claims, long expirationTimeMillis, String email) { // Generate a token for the user

        return Jwts.builder() // Create a new token builder
                .setClaims(claims) // Set the claims of the token
                .setSubject(email) // Set the subject of the token to the user's username
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issued date of the token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis)) // Set the expiration date of the token
                .signWith(getSigningKey()) // Sign the token with the secret key
                .compact(); // Compact the token
    }

//    public String extractRole(String token) { // Extract the role from the token
//        return extractClaims(token, claims -> claims.get("role", String.class)); // Return the role from the claims
//    }
//
//    public Long extractUserId(String token) { // Extract the user id from the token
//        return extractClaims(token, claims -> claims.get("user_id", Long.class)); // Return the user id from the claims
//    }

    public String generateAccessToken(String email) { // Generate an access token for the user
        // Access token valid for 5 minutes
        return generateToken(new HashMap<>(),1000 * 60 * 60*24, email); // Generate a token with the user's details
    }

    public String generateRefreshToken(String email) { // Generate a refresh token for the user
        // Refresh token valid for 3 days
        return generateToken(new HashMap<>(),1000 * 60 * 60 * 24 * 3, email); // Generate a token with the user's details
    }

}
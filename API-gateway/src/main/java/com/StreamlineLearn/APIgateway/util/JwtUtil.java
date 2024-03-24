package com.StreamlineLearn.APIgateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.function.Function;


@Service
public class JwtUtil {
    private final String SECRET_KEY = "1b21dd42f900ebd56d300289d02b2eeb8cc92cfaed9850c2e8579dd9f65a374a";


    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void validateToken(final String token){
        Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserRole(String token){
        token = token.trim();
        return extractClaim(token, claims -> claims.get("role", String.class));
    }



}

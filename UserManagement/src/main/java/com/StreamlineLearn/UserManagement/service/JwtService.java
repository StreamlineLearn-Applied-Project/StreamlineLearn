package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.UserManagement.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRET_KEY = "79e64193dac772a042fa99dc984a58dc17d15ebdc0352d8160fba52458b3b2d7";

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user, String role, Long roleId) {
        return Jwts
                .builder()
                .subject(user.getUserName())
                .claim("role",role) //Add the role of the user
                .claim("roleId", roleId) // Add roleId as a claim
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(getSignInKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUserName(String token){
        token = token.substring(7).trim();
        return extractClaim(token,Claims::getSubject);
    }

    public String extractUserRole(String token){
        token = token.substring(7).trim();
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public Long extractRoleId(String token) {
        token = token.substring(7).trim(); // Remove "Bearer " prefix
        return extractClaim(token, claims -> claims.get("roleId", Long.class));
    }


    public boolean isValid(String token, UserDetails userDetails){
        String username = extractUserName(token);

        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }


}

package com.StreamlineLearn.UserManagement.serviceImplementation;


import com.StreamlineLearn.UserManagement.dto.AuthenticationResponse;
import com.StreamlineLearn.UserManagement.jwtUtil.JwtService;
import com.StreamlineLearn.UserManagement.model.Token;
import com.StreamlineLearn.UserManagement.model.User;
import com.StreamlineLearn.UserManagement.repository.TokenRepository;
import com.StreamlineLearn.UserManagement.repository.UserRepository;
import com.StreamlineLearn.UserManagement.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {
    // Dependencies
    private final UserRepository repository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    // Logger for logging errors and other messages
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImplementation.class);

    // Constructor to inject dependencies
    public AuthenticationServiceImplementation(UserRepository repository,
                                               JwtService jwtService,
                                               TokenRepository tokenRepository,
                                               AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    // Method to revoke all tokens associated with a user
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    // Method to authenticate a user and generate JWT token
    public AuthenticationResponse authenticate(User request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = repository.findByUsername(request.getUsername()).orElseThrow();
            String token = jwtService.generateToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(token, user);

            return new AuthenticationResponse(token, user.getRole());
        }catch (AuthenticationException e) {
            // Logging authentication error
            logger.error("Authentication failed for user {}: {}", request.getUsername(), e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Authentication failed", e);
        } catch (Exception e) {
            // Logging general error
            logger.error("Error occurred during authentication: {}", e.getMessage());
            // Throwing a runtime exception
            throw new RuntimeException("Error occurred during authentication", e);
        }

    }
}

package com.StreamlineLearn.SharedModule.service;

import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserService {

    private static final int TOKEN_PREFIX_LENGTH = 7;
    private final SharedJwtService sharedJwtService;

    public JwtUserService(SharedJwtService sharedJwtService) {
        this.sharedJwtService = sharedJwtService;
    }

    public UserSharedDto extractJwtUser(String authorizationHeader) {
        String role = sharedJwtService.extractRole(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        Long roleId = sharedJwtService.extractRoleId(authorizationHeader.substring(TOKEN_PREFIX_LENGTH));
        return new UserSharedDto(roleId, role);
    }
}


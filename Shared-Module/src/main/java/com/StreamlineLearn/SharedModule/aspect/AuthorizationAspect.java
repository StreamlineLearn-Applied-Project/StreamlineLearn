package com.StreamlineLearn.SharedModule.aspect;

import com.StreamlineLearn.SharedModule.jwtUtil.SharedJwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthorizationAspect {
    private final SharedJwtService sharedJwtService;

    public AuthorizationAspect(SharedJwtService sharedJwtService) {
        this.sharedJwtService = sharedJwtService;
    }

    private void validateRole(String token, String requiredRole) {
        if (!sharedJwtService.isValidRole(token, requiredRole)) {
            throw new AccessDeniedException("Unauthorized access: Missing required role: " + requiredRole);
        }
    }

    // Helper method to extract the bearer token from the request
    private String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AccessDeniedException("Invalid or missing Authorization header");
        }
        return authorizationHeader.substring(7);
    }

    // Helper method to retrieve the current HTTP request
    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        return null;
    }

    @Pointcut("@annotation(com.StreamlineLearn.SharedModule.annotation.IsAdministrative)")
    public void administrativePointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.SharedModule.annotation.IsInstructor)")
    public void instructorPointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.SharedModule.annotation.IsStudent)")
    public void studentPointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.SharedModule.annotation.IsStudentOrInstructor)")
    public void studentOrInstructorPointcut() {}

    //    <--ADMINISTRATIVE-->

    @Before("administrativePointcut()")
    public void checkAdminAuthorization(JoinPoint joinPoint) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            throw new AccessDeniedException("No request context available");
        }
        String token = extractBearerToken(request);
        validateRole(token, "ADMINISTRATIVE");
    }

    //    <--Instructor-->

    @Before("instructorPointcut()")
    public void checkInstructorAuthorization(JoinPoint joinPoint) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            throw new AccessDeniedException("No request context available");
        }
        String token = extractBearerToken(request);
        validateRole(token, "INSTRUCTOR");
    }

//    <--Student-->

    @Before("studentPointcut()")
    public void checkStudentAuthorization(JoinPoint joinPoint) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            throw new AccessDeniedException("No request context available");
        }
        String token = extractBearerToken(request);
        validateRole(token, "STUDENT");
    }

    @Before("studentOrInstructorPointcut()")
    public void checkStudentOrInstructorAuthorization(JoinPoint joinPoint) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            throw new AccessDeniedException("No request context available");
        }
        String token = extractBearerToken(request);
        String role = sharedJwtService.extractRole(token);

        if (!role.equals("STUDENT") && !role.equals("INSTRUCTOR")) {
            throw new AccessDeniedException("Unauthorized access: User is neither a student nor an instructor");
        }
    }
}


package com.StreamlineLearn.UserManagement.aspect;

import com.StreamlineLearn.UserManagement.jwtUtil.JwtService;
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
    private final JwtService jwtService;

    public AuthorizationAspect(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private void validateRole(String token, String requiredRole) {
        if (!jwtService.isValidRole(token, requiredRole)) {
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

    @Pointcut("@annotation(com.StreamlineLearn.UserManagement.annotation.IsAdministrative)")
    public void administrativePointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.UserManagement.annotation.IsAuthorizedToUpdateAdministrative)")
    public void updateAdministrativePointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.UserManagement.annotation.IsAuthorizedToDeleteAdministrative)")
    public void deleteAdministrativePointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.UserManagement.annotation.IsInstructor)")
    public void instructorPointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.UserManagement.annotation.IsAuthorizedToUpdateInstructor)")
    public void updateInstructorPointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.UserManagement.annotation.IsAuthorizedToDeleteInstructor)")
    public void deleteInstructorPointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.UserManagement.annotation.IsStudent)")
    public void studentPointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.UserManagement.annotation.IsAuthorizedToUpdateStudent)")
    public void updateStudentPointcut() {}

    @Pointcut("@annotation(com.StreamlineLearn.UserManagement.annotation.IsAuthorizedToDeleteStudent)")
    public void deleteStudentPointcut() {}


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

    @Before("updateAdministrativePointcut() && args(id,..)")
    public void checkUpdateAdministrativeAuthorization(JoinPoint joinPoint, Long id) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            throw new AccessDeniedException("No request context available");
        }
        String token = extractBearerToken(request);

        // Check if the user is an ADMINISTRATIVE
        if (!jwtService.isValidRole(token, "ADMINISTRATIVE")) {
            throw new AccessDeniedException("Only administrative users can update administrative records");
        }

        // Additional authorization checks for updating administrative users
        // You can add custom logic here based on your requirements
    }

    @Before("deleteAdministrativePointcut() && args(id,..)")
    public void checkDeleteAdministrativeAuthorization(JoinPoint joinPoint, Long id) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            throw new AccessDeniedException("No request context available");
        }
        String token = extractBearerToken(request);

        // Check if the user is an ADMINISTRATIVE
        if (!jwtService.isValidRole(token, "ADMINISTRATIVE")) {
            throw new AccessDeniedException("Only administrative users can delete administrative records");
        }

        // Additional authorization checks for deleting administrative users
        // You can add custom logic here based on your requirements
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

    @Before("deleteInstructorPointcut()")
    public void checkDeleteInstructorAuthorization(JoinPoint joinPoint) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            throw new AccessDeniedException("No request context available");
        }
        String token = extractBearerToken(request);
        try {
            validateRole(token, "INSTRUCTOR");
        } catch (AccessDeniedException ex) {
            // If not an INSTRUCTOR, check if the user is an ADMINISTRATIVE
            validateRole(token, "ADMINISTRATIVE");
        }
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

    @Before("updateStudentPointcut() && args(id,..)")
    public void checkUpdateStudentAuthorization(JoinPoint joinPoint, Long id) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            throw new AccessDeniedException("No request context available");
        }
        String token = extractBearerToken(request);
        Long tokenStudentId = jwtService.extractRoleId(token);

        if (!tokenStudentId.equals(id)) {
            throw new AccessDeniedException("Students can only update their own records");
        }
    }

    @Before("deleteStudentPointcut() && args(id,..)")
    public void checkDeleteStudentAuthorization(JoinPoint joinPoint, Long id) {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            throw new AccessDeniedException("No request context available");
        }
        String token = extractBearerToken(request);
        Long tokenStudentId = jwtService.extractRoleId(token);

        // Check if the user is an ADMINISTRATIVE
        if (jwtService.isValidRole(token, "ADMINISTRATIVE")) {
            return; // Skip further checks if the user is an administrative
        }
        // If not an ADMINISTRATIVE, check if the user is the STUDENT with the matching ID
        if (!tokenStudentId.equals(id)) {
            throw new AccessDeniedException("Students can only delete their own records");
        }
    }

}


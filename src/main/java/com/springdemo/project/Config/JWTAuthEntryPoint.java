package com.springdemo.project.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Default to UNAUTHORIZED for general authentication issues
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String errorMessage = "Access Denied !! " + authException.getMessage();

        // You can inspect the type of AuthenticationException
        // to return more specific status codes
        if (authException instanceof BadCredentialsException) {
            statusCode = HttpServletResponse.SC_UNAUTHORIZED; // Still 401, but specifically bad credentials
            errorMessage = "Invalid credentials. Please check your username and password.";
        } else if (authException instanceof InsufficientAuthenticationException) {
            statusCode = HttpServletResponse.SC_FORBIDDEN; // 403 Forbidden for insufficient authentication (e.g., missing scope)
            errorMessage = "You do not have sufficient authentication to access this resource.";
        } else if (authException instanceof AccountExpiredException ||
                authException instanceof DisabledException ||
                authException instanceof LockedException ||
                authException instanceof CredentialsExpiredException) {
            // These are generally user account status issues, often still best as 401
            statusCode = HttpServletResponse.SC_UNAUTHORIZED;
            errorMessage = "Your account is " + authException.getMessage().toLowerCase() + ". Please contact support.";
        }
        // Add more specific checks if you have custom AuthenticationExceptions or needs

        response.setStatus(statusCode);
        response.setContentType("application/json"); // Often better to return JSON for API errors
        PrintWriter writer = response.getWriter();

        // Returning a structured JSON response is generally a good practice for APIs
        writer.println(String.format("{\"error\": \"%s\", \"message\": \"%s\"}",
                HttpServletResponse.SC_UNAUTHORIZED == statusCode ? "Unauthorized" : "Forbidden", // More descriptive error type
                errorMessage));
        writer.flush();
    }
}

package com.springsecurity.security;


import com.springsecurity.error.ApiError;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        String message = "Unauthorized";

        System.out.println(ex.getCause());

        Throwable cause = ex.getCause();
        if (cause instanceof io.jsonwebtoken.ExpiredJwtException) {
            message = "JWT token expired";
        } else if (cause instanceof io.jsonwebtoken.JwtException) {
            message = "Invalid JWT token";
        }

        ApiError apiError = new ApiError(message, HttpStatus.UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}


package com.albert.commerce.adapter.in.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        if (authentication.isAuthenticated()) {
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> authData = new HashMap<>();
            authData.put("name", authentication.getName());
            authData.put("authorities", authentication.getAuthorities());

            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(authData));
            writer.flush();
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        onAuthenticationSuccess(request, response, authentication);
        chain.doFilter(request, response);
    }
}

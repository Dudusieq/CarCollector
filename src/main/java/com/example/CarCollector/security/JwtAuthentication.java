package com.example.CarCollector.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthentication extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthentication(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("Extracted Token: " + token);
            if (jwtService.verifyToken(token)) {
                String username = jwtService.getUsernameFromJwt(token);
                System.out.println("Token valid for user: " + username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                System.out.println("Invalid JWT Token: " + token);
            }
        }else {
            System.out.println("No valid Authorization header found");
        }

        filterChain.doFilter(request, response);
    }
}

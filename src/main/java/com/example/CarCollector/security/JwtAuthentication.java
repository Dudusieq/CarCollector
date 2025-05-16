package com.example.CarCollector.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthentication extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthentication.class);

    private final JwtService jwtService;
    private final CustomUserDetailsService CustomuserDetailsService;

    public JwtAuthentication(JwtService jwtService, CustomUserDetailsService customuserDetailsService) {
        this.jwtService = jwtService;
        CustomuserDetailsService = customuserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            logger.info("Extracted Token: {}",token);
            if (jwtService.verifyToken(token)) {
                String username = jwtService.getUsernameFromJwt(token);
                logger.info("Token valid for user: {}",username);

                UserDetails userDetails = CustomuserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                logger.warn("Invalid JWT Token: {}",token);
            }
        }else {
            logger.warn("No valid Authorization header found");
        }

        filterChain.doFilter(request, response);
    }
}

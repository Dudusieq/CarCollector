package com.example.CarCollector.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationTest {

    private JwtService jwtService;
    private CustomUserDetailsService customUserDetailsService;
    private JwtAuthentication filter;
    private String validToken;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        customUserDetailsService = mock(CustomUserDetailsService.class);

        UserDetails ud = User.withUsername("user123")
                .password("")
                .authorities(Collections.emptyList())
                .build();
        when(customUserDetailsService.loadUserByUsername("user123"))
                .thenReturn(ud);

        filter = new JwtAuthentication(jwtService, customUserDetailsService);

        validToken = jwtService.generateToken("user123");
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_withValidToken_setsAuthentication() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer " + validToken);
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilterInternal(req, res, chain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth, "Powinien być ustawiony obiekt Authentication");
        assertEquals("user123", auth.getName());
        verify(chain).doFilter(req, res);
    }

    @Test
    void doFilterInternal_withInvalidToken_leavesContextUnauthenticated() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        req.addHeader("Authorization", "Bearer invalidtoken");
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilterInternal(req, res, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Brak validacji – Authentication powinien być null");
        verify(chain).doFilter(req, res);
    }

    @Test
    void doFilterInternal_withoutHeader_leavesContextUnauthenticated() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        filter.doFilterInternal(req, res, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Brak Authorization header – Authentication powinien być null");
        verify(chain).doFilter(req, res);
    }
}

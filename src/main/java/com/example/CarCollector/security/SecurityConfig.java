package com.example.CarCollector.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtToken jwtToken;

    public SecurityConfig(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthentication jwtAuthentication = new JwtAuthentication(jwtToken);

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**").permitAll() // Login dostępny bez autoryzacji
                                .anyRequest().authenticated()
                );

        // tutaj po dodaniu filtru JWT przed filtrem standardowej autentykacji po wykonaniu logowania nie zwraca tokenu
        // jak odkomentuje to localhost dziala a zwracanie tokenu nie a jak zakometuje to localhost dziala ale za to zwraca tokenu(sprawdzone w postmanie)
        //http.addFilterBefore(jwtAuthentication, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}

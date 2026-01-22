package com.example.FinalYearProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/index.html", "/indexlogin.html",
                    "/userlogin.html", "/usersignup.html",
                    "/companysignup.html", "/companylogin.html",
                    "/companydashboard.html", "/servicodashboard.html",
                    "/servicologin.html", "/userdashboard.html", "/companyprofile.html",

                    // ✅ API endpoints
                    "/api/users/**", 
                    "/api/services/**",
                    "/api/company/**",
                    "/api/admin/**",
                    "/api/servicodashboard/**",
                    "/api/companydashboard/**",
                    "/api/usersdashboard/**",
                    "/api/bookings/**", // already includes /reviews/all
                    "/api/review/**",
                    "/api/reviews/getall",
                    

                    // ✅ Static files
                    "/styles.css", "/app.js", "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
}

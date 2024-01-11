package com.neobis.yerokha.beernestspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .securityMatcher("v1/**")
                .authorizeHttpRequests(cAuthz ->
                        cAuthz
                                .requestMatchers("/beers/**").permitAll()
                                .requestMatchers("/register", "/login", "/users/recovery").anonymous()
                                .requestMatchers("/users/**", "/orders/**").authenticated()
                );

        http
                .securityMatcher("api/admin/**")
                .authorizeHttpRequests(authz ->
                        authz
                                .anyRequest().hasAuthority("ADMIN")
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
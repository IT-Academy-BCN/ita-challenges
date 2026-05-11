package com.ita.challenges.account.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/account/auth/**").permitAll()
                        .requestMatchers("/api/account/oauth2/**").permitAll()
                        .requestMatchers("/api/account/login/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/api/account/oauth2/authorization/github")
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/api/account/oauth2/authorization")
                        )
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/api/account/login/oauth2/code/**")
                        )
                        .defaultSuccessUrl("/profile", true)
                );
        return http.build();
    }
}

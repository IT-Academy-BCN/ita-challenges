package com.ita.challenges.account.infrastructure.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest
@Import(SecurityConfig.class)
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Test
    @DisplayName("Should assure the SecurityFilterChain is loaded")
    void contextLoads() {
        assertThat(securityFilterChain).isNotNull();
    }

    @Test
    @DisplayName("Should permit all requests to /api/account/auth/** endpoints")
    void shouldPermitAllRequestsToAuthEndpoints() throws Exception {
        mockMvc.perform(get("/api/account/auth/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should require authentication for requests to other endpoints")
    void shouldRequireAuthenticationForOtherRequests() throws Exception {
        mockMvc.perform(get("/api/other"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/oauth2/authorization/github"));
    }

    @Test
    @DisplayName("Should configure OAuth2 login and redirect to authorization endpoint")
    void shouldConfigureOAuth2Login() throws Exception {
        mockMvc.perform(get("/api/protected"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/oauth2/authorization/github"));
    }
} 

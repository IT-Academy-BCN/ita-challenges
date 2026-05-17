package com.ita.challenges.account.infrastructure.config;

import com.ita.challenges.account.domain.port.out.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
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

 @MockBean
 private ClientRegistrationRepository clientRegistrationRepository;

 @MockBean
 private TicketRepository ticketRepository;

 @Test
 @DisplayName("Should assure the SecurityFilterChain is loaded")
 void contextLoads() {
  assertThat(securityFilterChain).isNotNull();
 }

 @ParameterizedTest
 @ValueSource(strings = {
  "/api/account/auth/me",
  "/api/account/oauth2/authorization/github",
  "/api/account/login/oauth2"
 })
 @DisplayName("Should permit all requests to public endpoints")
 void shouldPermitAllRequestsToPublicEndpoints(String path) throws Exception {
  mockMvc.perform(get(path))
   .andExpect(result -> {
    int status = result.getResponse().getStatus();
    org.junit.jupiter.api.Assertions.assertNotEquals(HttpStatus.FORBIDDEN.value(), status);
   });
 }

 @Test
 @DisplayName("Should require authentication for protected endpoints and redirect to OAuth2")
 void shouldRequireAuthenticationForProtectedEndpoints() throws Exception {
  mockMvc.perform(get("/api/account/other"))
   .andExpect(status().is3xxRedirection())
   .andExpect(redirectedUrlPattern("**/auth"));
 }

}

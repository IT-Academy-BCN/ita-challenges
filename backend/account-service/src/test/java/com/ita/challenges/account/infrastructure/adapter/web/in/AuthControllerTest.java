package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.infrastructure.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = "server.servlet.context-path=/api/account")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Test
    void authMe_whenAuthenticated_returnsUsernameAndAvatarUrl() throws Exception {
        mockMvc.perform(get("/api/account/auth/me")
                        .contextPath("/api/account")
                        .with(oauth2Login()
                                .attributes(attrs -> {
                                    attrs.put("login", "testuser");
                                    attrs.put("avatar_url", "https://github.com/testuser.png");
                                })))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.avatarUrl").value("https://github.com/testuser.png"));
    }

    @Test
    void authMe_whenNotAuthenticated_returnsAnonymous() throws Exception {
        mockMvc.perform(get("/api/account/auth/me")
                        .contextPath("/api/account"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("anonymous"));
    }
}
package com.ita.challenges.account.infrastructure.adapter.web.in;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create_shouldReturn200() throws Exception {
        mockMvc.perform(post("/api/account/auth/register")
                        .with(oauth2Login())
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @Test
    void me_whenAuthenticated_returnsUsernameAndAvatarUrl() throws Exception {
        mockMvc.perform(get("/api/account/me")
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
    void me_whenNotAuthenticated_redirectsToGitHub() throws Exception {
        mockMvc.perform(get("/api/account/me"))
                .andExpect(status().isFound()); // 302
    }
}
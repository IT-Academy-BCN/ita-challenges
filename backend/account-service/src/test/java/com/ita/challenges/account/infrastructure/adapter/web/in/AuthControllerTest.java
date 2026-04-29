package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.infrastucture.adpter.web.in.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void me_whenAuthenticated_returnsOk() throws Exception {
        mockMvc.perform(get("/api/account/auth/me")
                        .with(oauth2Login()))
                .andExpect(status().isOk());
    }

    @Test
    void me_whenNotAuthenticated_redirectsToGitHub() throws Exception {
        mockMvc.perform(get("/api/account/me"))
                .andExpect(status().isFound()); // 302
    }
}
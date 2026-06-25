package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@Import(UserControllerTest.TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Test
    @WithMockUser
    void shouldReturnUserRoleWhenUserExists() throws Exception {
        User user = new User("testuser", Role.STUDENT);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/account/users/testuser/role"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("STUDENT"));
    }

    @Test
    @WithMockUser
    void shouldReturn404WhenUserNotFound() throws Exception {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/account/users/unknown/role"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUserProfileWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/api/account/users/me")
                        .with(oauth2Login().attributes(attrs -> {
                            attrs.put("login", "alex-frontend");
                            attrs.put("avatar_url", "https://github.com/avatar.png");
                        })))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("alex-frontend"))
                .andExpect(jsonPath("$.avatarUrl").value("https://github.com/avatar.png"));
    }

    @Test
    void shouldReturn401WhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/account/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllMentors_ShouldReturnListOfUserResponseAndStatusOk() throws Exception {
        User mentor1 = new User("juan123", Role.MENTOR);
        User mentor2 = new User("maria456", Role.MENTOR);

        when(userRepository.findAllMentors())
                .thenReturn(List.of(mentor1, mentor2));


        mockMvc.perform(get("/api/account/users/mentors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].username").value("juan123"))
                .andExpect(jsonPath("$[0].role").value("MENTOR"))
                .andExpect(jsonPath("$[1].username").value("maria456"))
                .andExpect(jsonPath("$[1].role").value("MENTOR"));
    }

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
            return http.build();
        }
    }
}

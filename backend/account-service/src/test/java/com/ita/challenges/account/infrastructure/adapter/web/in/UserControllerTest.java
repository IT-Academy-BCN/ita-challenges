package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
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
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/account/users/unknown/role"))
                .andExpect(status().isNotFound());
    }
}

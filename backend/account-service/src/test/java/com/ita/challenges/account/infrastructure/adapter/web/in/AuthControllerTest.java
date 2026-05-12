package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(AuthController.class)
@AutoConfigureMockRestServiceServer
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository repository;

    @Autowired
    private AuthController authController;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();

        mockServer = MockRestServiceServer.bindTo(builder).build();

        RestClient testClient = builder.build();
        ReflectionTestUtils.setField(authController, "restClient", testClient);
    }

    @Test
    void create_shouldReturn201WhenUserIsNew() throws Exception {
        this.mockServer.expect(requestTo("https://api.github.com/users/username12345"))
                .andRespond(withSuccess("{\"id\": \"ID12345\"}", MediaType.APPLICATION_JSON));

        when(repository.findByUsername("ID12345")).thenReturn(Optional.empty());

        User saved = new User("ID12345", Role.GUEST);
        when(repository.save(any(User.class))).thenReturn(saved);

        UserRequest request = new UserRequest("username12345", Role.GUEST);
        mockMvc.perform(post("/api/account/auth/register")
                        .with(oauth2Login())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("ID12345"))
                .andExpect(jsonPath("$.role").value(Role.GUEST.name()));
    }

    @Test
    void create_shouldReturn200AndUpdateUserWhenAlreadyExists() throws Exception {
        this.mockServer.expect(requestTo("https://api.github.com/users/username12345"))
                .andRespond(withSuccess("{\"id\": \"ID12345\"}", MediaType.APPLICATION_JSON));

        User existingUser = new User("ID12345", Role.GUEST);
        when(repository.findByUsername("ID12345")).thenReturn(Optional.of(existingUser));

        User saved = new User("ID12345", Role.STUDENT);
        when(repository.save(any(User.class))).thenReturn(saved);

        UserRequest request = new UserRequest("username12345", Role.STUDENT);
        mockMvc.perform(post("/api/account/auth/register")
                        .with(oauth2Login()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ID12345"))
                .andExpect(jsonPath("$.role").value(Role.STUDENT.name()));
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
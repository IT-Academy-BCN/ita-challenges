package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.AuthUserDto;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserResponse;
import com.ita.challenges.account.infrastructure.adapter.web.out.dto.GitHubUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AuthController {

    private final UserRepository repository;
    private final RestClient restClient;

    public AuthController(UserRepository repository, RestClient restClient) {
        this.repository = repository;
        this.restClient = restClient;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        GitHubUserResponse gitHubUserResponse = restClient.get()
                .uri("https://api.github.com/users/{username}", request.username())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GitHub user not found");
                })
                .body(GitHubUserResponse.class);

        Optional<User> userOptional = repository.findByUsername(gitHubUserResponse.id());
        User user;
        HttpStatus status;

        if (userOptional.isPresent()) {
            user = userOptional.get().withRole(request.role());
            status = HttpStatus.OK;
        } else {
            user = new User(gitHubUserResponse.id(), request.role());
            status = HttpStatus.CREATED;
        }

        User saved = repository.save(user);

        return ResponseEntity.status(status).body(
                new UserResponse(
                        saved.userName(),
                        saved.userRole()
                )
        );
    }
}

package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.AuthUserDto;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.GitHubUserResponse;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AuthController {

    private final UserRepository repository;
    private final RestTemplate restTemplate;

    public AuthController(UserRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        String url = "https://api.github.com/users/" + request.username();

        GitHubUserResponse gitHubUserResponse = restTemplate.getForObject(url, GitHubUserResponse.class);

        User user = new User(gitHubUserResponse.id(), request.role());

        User saved = repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserResponse(
                        saved.userName(),
                        saved.userRole()
                )
        );
    }

   @GetMapping("/me")
    public ResponseEntity<AuthUserDto> me(@AuthenticationPrincipal OAuth2User user) {

        if (user == null) {
            return ResponseEntity.ok(new AuthUserDto("anonymous", null));
        }

        String login = user.getAttribute("login");
        String avatarUrl = user.getAttribute("avatar_url");

        if (login == null) {
            login = "unknown";
        }

        AuthUserDto dto = new AuthUserDto(login, avatarUrl);
        return ResponseEntity.ok(dto);
    }
}

package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.GitHubUserResponse;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/api/account/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserRepository repository;
    private final RestTemplate restTemplate;

    public UserController(UserRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        String url = "https://api.github.com/users/" + request.username();
        
        GitHubUserResponse gitHubUserResponse = restTemplate.getForObject(url, GitHubUserResponse.class);

        if (gitHubUserResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

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

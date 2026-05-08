package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        User user = new User(request.username(), request.role());
        User saved = repository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UserResponse(
                        saved.userName(),
                        saved.userRole()
                )
        );
    }
}

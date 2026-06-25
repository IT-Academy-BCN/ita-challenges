package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.domain.port.out.UserRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.AuthUserDto;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserResponse;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.UserRoleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/account/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}/role")
    public ResponseEntity<UserRoleResponse> getUserRole(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(user -> ResponseEntity.ok(new UserRoleResponse(user.userRole())))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthUserDto> authMe(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(mapUser(user));
    }

    private AuthUserDto mapUser(OAuth2User user) {
        String login = user.getAttribute("login");
        String avatarUrl = user.getAttribute("avatar_url");

        if (login == null) {
            login = "unknown";
        }

        return new AuthUserDto(login, avatarUrl);
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<UserResponse>> getAllMentors() {
        return ResponseEntity.ok(userRepository.findAllMentors().stream()
                .map(user -> new UserResponse(user.userName(), user.userRole()))
                .toList());
    }
}

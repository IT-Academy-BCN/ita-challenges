package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.infrastructure.adapter.web.in.dto.AuthUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/account")
public class AuthController {

    @GetMapping("/auth/me")
    public ResponseEntity<AuthUserDto> authMe(@AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.ok(mapUser(user));
    }

    private AuthUserDto mapUser(OAuth2User user) {
        if (user == null) {
            return new AuthUserDto("anonymous", null);
        }

        String login = user.getAttribute("login");
        String avatarUrl = user.getAttribute("avatar_url");

        if (login == null) {
            login = "unknown";
        }

        return new AuthUserDto(login, avatarUrl);
    }

    @GetMapping("/auth/login")
    public ResponseEntity<Void> login() {
        return ResponseEntity.status(302)
                .location(URI.create("/oauth2/authorization/github"))
                .build();
    }
}

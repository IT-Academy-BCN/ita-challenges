package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.ita.challenges.account.infrastructure.adapter.web.in.dto.AuthUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AuthController {

    @PostMapping("/auth/register")
    public ResponseEntity<Void> create() {
        return ResponseEntity.status(HttpStatus.OK).build();
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

package com.ita.challenges.account.infrastucture.adpter.web.in;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/auth")
public class AuthController {

    // GET /api/account/login
    // No method needed — Spring Security owns this route.
    // It redirects the user to GitHub automatically via SecurityConfig.

    // GET /api/account/me
    // Scaffold only — Task 2 will fill in username + avatarUrl.
    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal OAuth2User user) {
        return ResponseEntity.ok().build();
    }
}

package com.ita.challenges.account.infrastructure.adapter.web.in;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/auth")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<Void> me(@AuthenticationPrincipal OAuth2User user) {

        return ResponseEntity.ok().build();
    }
}


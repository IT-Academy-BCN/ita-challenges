package com.ita.challenges.account.infrastucture.adapter.web.in;

import com.ita.challenges.account.infrastucture.adapter.web.in.dto.AuthUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AuthController {

    @GetMapping("/me")
    public ResponseEntity<AuthUserDto> me(@AuthenticationPrincipal OAuth2User user) {
        AuthUserDto dto = new AuthUserDto(
                user.getAttribute("login"),     // GitHub username
                user.getAttribute("avatar_url") // GitHub avatar
        );
        return ResponseEntity.ok(dto);
    }
}
package com.ita.challenges.account.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController

public class HomeController {
    @GetMapping ("/api/account/success")
    public Map<String, Object> success(@AuthenticationPrincipal OAuth2User principal){
        return Map.of(
                "username", principal.getAttribute("login"),
                "avatarUrl",principal.getAttribute("avatar_url") !=null
                ? principal.getAttribute("avatar_url"): ""
        );
    }
}

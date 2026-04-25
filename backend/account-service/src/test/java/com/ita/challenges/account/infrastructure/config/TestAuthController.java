package com.ita.challenges.account.infrastructure.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAuthController {

    @GetMapping("/api/account/auth/test-ok")
    public String testOk() {
        return "OK";
    }
}

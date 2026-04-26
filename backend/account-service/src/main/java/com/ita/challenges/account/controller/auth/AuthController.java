package com.ita.challenges.account.controller.auth;


import com.ita.challenges.account.dto.auth.AuthUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account/auth")
public class AuthController {
    @GetMapping("/me")
    public AuthUserDto me(Authentication authentication){
    return new AuthUserDto(authentication.getName());
    }
}

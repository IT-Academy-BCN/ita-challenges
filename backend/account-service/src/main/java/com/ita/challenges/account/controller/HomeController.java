package com.ita.challenges.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController {
    @GetMapping ("/api/account/success")
    public String home(){
        return "Home Successfully";
    }
}

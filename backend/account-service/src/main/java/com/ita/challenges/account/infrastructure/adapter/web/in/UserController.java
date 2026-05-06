package com.ita.challenges.account.infrastructure.adapter.web.in;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/users")
public class UserController {

    @PostMapping
    public ResponseEntity<Void> create() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

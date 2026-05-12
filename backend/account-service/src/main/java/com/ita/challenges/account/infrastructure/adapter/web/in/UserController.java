package com.ita.challenges.account.infrastructure.adapter.web.in;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
}

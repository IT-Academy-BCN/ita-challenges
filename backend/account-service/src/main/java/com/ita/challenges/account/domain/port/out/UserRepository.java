package com.ita.challenges.account.domain.port.out;

import com.ita.challenges.account.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
}
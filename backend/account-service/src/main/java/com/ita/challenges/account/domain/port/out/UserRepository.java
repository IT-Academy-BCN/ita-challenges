package com.ita.challenges.account.domain.port.out;

import com.ita.challenges.account.domain.model.User;

public interface UserRepository {
    void save(User user);
}

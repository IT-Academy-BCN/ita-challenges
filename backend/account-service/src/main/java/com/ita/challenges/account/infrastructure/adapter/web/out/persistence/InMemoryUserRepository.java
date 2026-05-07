package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryUserRepository implements UserRepository {
    public final ConcurrentMap<String, User> storage = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        storage.put(user.userName(), user);
    }
}

package com.ita.challenges.account.infrastructure.out.persistence;

import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.infrastructure.adapter.web.out.persistence.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryUserRepositoryTest {
    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void should_save_a_user() {
        User user = new User("john", Role.ADMIN);

        repository.save(user);

        assertThat(repository.storage.get("john")).isEqualTo(user);
    }

    @Test
    void should_overwrite_existing_user() {
        repository.save(new User("john", Role.GUEST));
        repository.save(new User("john", Role.ADMIN));

        assertThat(repository.storage.get("john").role()).isEqualTo(Role.ADMIN);
    }

}

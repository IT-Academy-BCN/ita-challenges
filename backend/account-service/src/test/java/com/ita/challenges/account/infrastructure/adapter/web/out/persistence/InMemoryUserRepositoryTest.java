package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {
    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void should_return_empty_when_user_not_found() {
        assertThat(repository.findByUsername("unknown")).isEmpty();
    }

    @Test
    void should_save_a_user() {
        User user = new User("john", Role.MENTOR);
        repository.save(user);
        assertThat(repository.storage).containsEntry("john", user);
    }

    @Test
    void should_overwrite_existing_user() {
        repository.save(new User("john", Role.GUEST));
        repository.save(new User("john", Role.MENTOR));
        assertThat(repository.storage.get("john").userRole()).isEqualTo(Role.MENTOR);
    }

    @Test
    void save_shouldSaveAndReturnUser() {
        User user = new User("ID12345", Role.GUEST);

        User savedUser = repository.save(user);

        assertNotNull(savedUser);
        assertEquals("ID12345", savedUser.userName());

        Optional<User> found = repository.findByUsername("ID12345");
        assertTrue(found.isPresent());
        assertEquals(Role.GUEST, found.get().userRole());
    }
}

package com.ita.challenges.account.infrastructure.seed;

import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersSeedLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    public UsersSeedLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        List<User> initialUsers = List.of(
                new User("John Peter", Role.MENTOR),
                new User("Jane Doe", Role.MENTOR),
                new User("Alice Smith", Role.MENTOR),
                new User("Bob Johnson", Role.GUEST),
                new User("Jane Lee", Role.GUEST),
                new User("Charlie Brown", Role.STUDENT),
                new User("Emily Davis", Role.STUDENT)
        );
        initialUsers.forEach(userRepository::save);
    }
}
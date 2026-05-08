package com.ita.challenges.account.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {


    @Test
    void shouldCreateUserWithValidUserNameAndRole() {

        String userName = "testUser";
        Role userRole = Role.STUDENT;

        User user = new User(userName, userRole);

        assertNotNull(user);
        assertEquals(userName, user.getUserName());
        assertEquals(userRole, user.getUserRole());
    }

}

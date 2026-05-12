package com.ita.challenges.account.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Test
    void shouldCreateUserWithValidUserNameAndRole() {

        String userName = "testUser";
        Role userRole = Role.STUDENT;

        User user = new User(userName, userRole);

        assertNotNull(user);
        assertEquals(userName, user.userName());
        assertEquals(userRole, user.userRole());
    }

    @Test
    void withRole_shouldReturnNewUserWithUpdatedRole() {
        User user = new User("ID12345", Role.GUEST);

        User updatedUser = user.withRole(Role.STUDENT);

        assertEquals(Role.STUDENT, updatedUser.userRole());
        assertEquals("ID12345", updatedUser.userName());

        assertNotSame(user, updatedUser);
        assertEquals(Role.GUEST, user.userRole());
    }

}

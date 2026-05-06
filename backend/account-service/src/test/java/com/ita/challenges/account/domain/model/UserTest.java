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
        assertEquals(userName, user.getUserName());
        assertEquals(userRole, user.getUserRole());
    }

    @Test
    void shouldThrowExceptionWhenUserNameIsNull() {

        String userName = null;
        Role userRole = Role.STUDENT;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new User(userName, userRole);
        });
        assertEquals("User name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserNameIsEmpty() {

        String userName = "";
        Role userRole = Role.STUDENT;


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new User(userName, userRole);
        });
        assertEquals("User name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserNameIsBlank() {

        String userName = "   ";
        Role userRole = Role.STUDENT;


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new User(userName, userRole);
        });
        assertEquals("User name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserRoleIsNull() {

        String userName = "testUser";
        Role userRole = null;


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new User(userName, userRole);
        });
        assertEquals("User role cannot be null", exception.getMessage());
    }
}

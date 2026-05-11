package com.ita.challenges.account.domain.model;

public class User {
    private final String userName;
    private final Role userRole;

    public User(String userName, Role userRole) {

        this.userName = userName;
        this.userRole = userRole;
    }

    public String userName() {
        return userName;
    }

    public Role userRole() {
        return userRole;
    }
}
package com.ita.challenges.account.domain.model;

public class User {
    private final String userName;
    private final Role userRole;

    public User(String userName, Role userRole) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (userRole == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }
        this.userName = userName;
        this.userRole = userRole;
    }
    public String getUserName() {
        return userName;
    }
    public Role getUserRole() {
        return userRole;
    }


}
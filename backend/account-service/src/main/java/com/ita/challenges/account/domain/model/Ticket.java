package com.ita.challenges.account.domain.model;

import java.util.UUID;

public class Ticket {

    private final String id;
    private final String userId;
    private final String title;
    private final String description;

    private Ticket(String id, String userId, String title, String description) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    public static Ticket create(String userId, String title, String description) {
        return new Ticket(
                UUID.randomUUID().toString(),
                userId,
                title,
                description
        );
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

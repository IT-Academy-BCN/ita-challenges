package com.ita.challenges.account.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Ticket {

    private final String id;
    private final String userId;
    private final String mentorAssignedId;
    private final String title;
    private final String description;
    private final TicketStatus status;
    private final String comment;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Ticket(String id, String userId, String mentorAssignedId, String title, String description,
                   TicketStatus status, String comment, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.mentorAssignedId = mentorAssignedId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.comment = comment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Ticket create(String userId, String title, String description) {
        Instant now = Instant.now();
        return new Ticket(
                UUID.randomUUID().toString(),
                userId,
                null,
                title,
                description,
                TicketStatus.OPEN,
                null,
                now,
                now
        );
    }

    @Deprecated
    public static Ticket restore(String id, String userId, String mentorAssignedId, String title, String description) {
        return new Ticket(
                id,
                userId,
                mentorAssignedId,
                title,
                description,
                TicketStatus.OPEN,
                null,
                Instant.now(),
                Instant.now()
        );
    }

    public static Ticket restore(String id, String userId, String mentorAssignedId, String title, String description,
                                 TicketStatus status, String comment, Instant createdAt, Instant updatedAt) {
        return new Ticket(id, userId, mentorAssignedId, title, description, status, comment, createdAt, updatedAt);
    }
    public Ticket withUpdates(TicketStatus status, String comment) {
        return new Ticket(
                this.id,
                this.userId,
                this.mentorAssignedId,
                this.title,
                this.description,
                status != null ? status : this.status,
                comment != null ? comment : this.comment,
                this.createdAt,
                Instant.now()
        );
    }

    public Ticket assignMentor(String mentorId) {
        return new Ticket(
                this.id,
                this.userId,
                mentorId,
                this.title,
                this.description,
                this.status,
                this.comment,
                this.createdAt,
                Instant.now()
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
    public TicketStatus getStatus() {
        return status;
    }
    public String getComment() {
        return comment;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getMentorAssignedId() {
        return mentorAssignedId;
    }
}

package com.ita.challenges.account.domain.model;

import com.ita.challenges.account.domain.valueobject.TicketDescription;
import com.ita.challenges.account.domain.valueobject.TicketId;
import com.ita.challenges.account.domain.valueobject.TicketTitle;
import com.ita.challenges.account.domain.valueobject.UserId;
import lombok.Getter;

@Getter
public class Ticket {

    private final TicketId id;
    private final UserId userId;
    private final TicketTitle title;
    private final TicketDescription description;

    private Ticket(TicketId id, UserId userId, TicketTitle title, TicketDescription description) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    public static Ticket create(String userId, String title, String description) {
        return new Ticket(
                TicketId.generate(),
                new UserId(userId),
                new TicketTitle(title),
                new TicketDescription(description)
        );
    }
}

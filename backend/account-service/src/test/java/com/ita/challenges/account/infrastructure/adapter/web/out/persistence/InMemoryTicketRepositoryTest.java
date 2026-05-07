package com.ita.challenges.account.infrastructure.adapter.web.out.persistence;

import com.ita.challenges.account.domain.model.Ticket;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryTicketRepositoryTest {

    private final InMemoryTicketRepository repository = new InMemoryTicketRepository();

    @Test
    void should_return_empty_list_when_no_tickets_exist_for_user() {
        List<Ticket> result = repository.findAllByUserId("user-1");

        assertThat(result).isEmpty();
    }
}

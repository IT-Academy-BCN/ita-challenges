package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TicketController.class)
@TestPropertySource(properties = "server.servlet.context-path=/api/account")
class TicketControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    void should_return_200_with_ticket_list_when_requesting_ticket_list() throws Exception {
        Ticket ticket = Ticket.create("testuser", "Login issue", "Unable to access my account");
        when(ticketRepository.findAllByUserId("testuser")).thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/account/tickets")
                        .contextPath("/api/account")
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", "testuser"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value("testuser"))
                .andExpect(jsonPath("$[0].title").value("Login issue"))
                .andExpect(jsonPath("$[0].description").value("Unable to access my account"));
    }

    @Test
    void should_update_ticket_successfully() throws Exception {
        String ticketId = "ticket-123";
        String currentUserId = "user-1";

        Ticket existingTicket = Ticket.restore(ticketId, currentUserId, "Old Title", "Old Desc");
        TicketRequest updateRequest = new TicketRequest("New Title", "New Desc");

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.updateTicket(any(Ticket.class))).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(put("/api/account/tickets/{id}", ticketId)
                        .contextPath("/api/account")
                        .with(csrf())
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", currentUserId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketId))
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.userId").value(currentUserId));
    }
    @Test
    void should_return_404_when_updating_non_existing_ticket() throws Exception {
        String ticketId = "non-existent";
        TicketRequest updateRequest = new TicketRequest("Title", "Desc");

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/account/tickets/{id}", ticketId)
                        .with(csrf())
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", "any-user")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_403_when_user_is_not_the_owner() throws Exception {
        String ticketId = "ticket-123";
        String ownerId = "user-1";
        String hackerId = "hacker-99";

        Ticket existingTicket = Ticket.restore(ticketId, ownerId, "Title", "Desc");
        TicketRequest updateRequest = new TicketRequest("New Title", "New Desc");

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));

        mockMvc.perform(put("/api/account/tickets/{id}", ticketId)
                        .contextPath("/api/account")
                        .with(csrf())
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", hackerId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isForbidden());
    }
}

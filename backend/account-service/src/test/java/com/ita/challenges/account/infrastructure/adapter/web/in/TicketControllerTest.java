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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketRepository ticketRepository;

    @Test
    @WithMockUser
    void should_return_200_with_empty_list_when_requesting_ticket_list() throws Exception {
        mockMvc.perform(get("/api/account/tickets"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser
    void should_update_ticket_successfully() throws Exception {
        String ticketId = "ticket-123";
        Ticket existingTicket = Ticket.create("user-1", "Old Title", "Old Desc");
        TicketRequest updateRequest = new TicketRequest("New Title", "New Desc");


        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));

        when(ticketRepository.updateTicket(any(Ticket.class))).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(put("/api/account/tickets/{id}", ticketId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketId))
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.userId").value("user-1")); // 确保 userId 没变
    }

    @Test
    @WithMockUser
    void should_return_404_when_updating_non_existing_ticket() throws Exception {
        String ticketId = "non-existent";
        TicketRequest updateRequest = new TicketRequest("Title", "Desc");

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/account/tickets/{id}", ticketId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }
}

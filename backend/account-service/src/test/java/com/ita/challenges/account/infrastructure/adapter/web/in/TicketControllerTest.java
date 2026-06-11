package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ita.challenges.account.domain.model.Role;
import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.model.TicketStatus;
import com.ita.challenges.account.domain.model.User;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import com.ita.challenges.account.domain.port.out.UserRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketPatchRequest;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.Instant;
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
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void should_create_ticket_and_return_201_created() throws Exception {
        TicketRequest request = new TicketRequest("Test Title", "Test description");
        Ticket mockTicket = Ticket.restore("ticket-123", "tester", request.title(), request.description());

        when(ticketRepository.save(any(Ticket.class))).thenReturn(mockTicket);

        mockMvc.perform(post("/api/account/tickets")
                        .with(csrf())
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", "tester")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("ticket-123"))
                .andExpect(jsonPath("$.userId").value("tester"))
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.description").value("Test description"));
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void should_return_400_when_sending_blank_fields_in_create_ticket() throws Exception {
        TicketRequest request = new TicketRequest("", "");

        mockMvc.perform(post("/api/account/tickets")
                        .with(csrf())
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", "tester")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No empty fields allowed"));

    }

    @Test
    void should_return_200_with_ticket_list_when_requesting_ticket_list() throws Exception {
        Ticket ticket = Ticket.create("testuser", "Login issue", "Unable to access my account");
        when(ticketRepository.findAllByUserId("testuser")).thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/account/tickets")
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
                        .with(csrf())
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", hackerId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    void should_return_200_with_ticket_when_requesting_ticket() throws Exception {

        String ticketId = "ticket-123";
        String ownerId = "user-11";

        Ticket existingTicket = Ticket.restore(ticketId, ownerId, "Title for test", "Description for tests");

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));

        mockMvc.perform(get("/api/account/tickets/{id}", ticketId)
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", ownerId))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(ownerId))
                .andExpect(jsonPath("$.title").value("Title for test"))
                .andExpect(jsonPath("$.description").value("Description for tests"));
    }

    @Test
    void should_patch_ticket_successfully_with_partial_new_fields() throws Exception {
        String ticketId = "ticket-123";
        String currentUserId = "user-1";

        Ticket existingTicket = Ticket.restore(ticketId, currentUserId, "Old Title", "Old Desc");
        TicketPatchRequest patchRequest = new TicketPatchRequest(TicketStatus.IN_PROGRESS, "Working on it");

        User mockUser = new User(currentUserId, Role.MENTOR);
        when(userRepository.findByUsername(currentUserId)).thenReturn(Optional.of(mockUser));

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.updateTicket(any(Ticket.class))).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(patch("/api/account/tickets/{id}", ticketId)
                        .with(csrf())
                        .with(oauth2Login().attributes(attrs -> {
                            attrs.put("login", currentUserId);
                        }))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ticketId))
                .andExpect(jsonPath("$.title").value("Old Title"))
                .andExpect(jsonPath("$.description").value("Old Desc"))
                .andExpect(jsonPath("$.ticketStatus").value("IN_PROGRESS"))
                .andExpect(jsonPath("$.ticketComment").value("Working on it"));
    }


    @Test
    void should_return_200_with_ticket_list_when_user_is_authenticated() throws Exception {
        String userId = "user-42";
        Ticket ticket = Ticket.restore("ticket-999", userId, "Payment error", "Card declined");

        when(ticketRepository.findAllByUserId(userId)).thenReturn(List.of(ticket));

        mockMvc.perform(get("/api/account/tickets")
                        .with(oauth2Login().attributes(attrs -> attrs.put("login", userId))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("ticket-999"))
                .andExpect(jsonPath("$[0].userId").value(userId))
                .andExpect(jsonPath("$[0].title").value("Payment error"))
                .andExpect(jsonPath("$[0].description").value("Card declined"));
    }
}

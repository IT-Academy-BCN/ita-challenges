package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ita.challenges.account.domain.model.Ticket;
import com.ita.challenges.account.domain.port.out.TicketRepository;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
<<<<<<< feature/478-create-ticket
=======
import jakarta.servlet.ServletException;
>>>>>>> develop
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

<<<<<<< feature/478-create-ticket
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
=======
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
>>>>>>> develop
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

<<<<<<< feature/478-create-ticket
    @Autowired
    private ObjectMapper objectMapper;

=======
>>>>>>> develop
    @MockBean
    private TicketRepository ticketRepository;

    @Test
<<<<<<< feature/478-create-ticket
    void should_create_ticket_and_return_201_created() throws Exception {
        TicketRequest request = new TicketRequest("Test Title", "Test description");
        Ticket mockTicket = Ticket.create("12345678", request.title(), request.description());

        when(ticketRepository.save(any(Ticket.class))).thenReturn(mockTicket);

        mockMvc.perform(post("/api/account/tickets")
                        .with(csrf())
                        .with(oauth2Login().attributes(attrs -> attrs.put("id", 12345678)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

=======
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
  
>>>>>>> develop
    @Test
    @WithMockUser
    void should_return_error_when_updating_ticket_because_not_implemented() throws Exception {

        String ticketId = "123";
        TicketRequest request = new TicketRequest("title", "description");

        ServletException exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(put("/api/account/tickets/{id}", ticketId)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
        });

        assertInstanceOf(UnsupportedOperationException.class, exception.getCause());
        assertEquals("Update endpoint not implemented yet for ID: 123", exception.getCause().getMessage());
    }
}
package com.ita.challenges.account.infrastructure.adapter.web.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ita.challenges.account.infrastructure.adapter.web.in.dto.TicketRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void shouldLoadTicketEndpoint() throws Exception {
        mockMvc.perform(get("/api/account/tickets"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void should_return_error_when_updating_ticket_because_not_implemented() throws Exception {

        String ticketId = "123";
        TicketRequest request = new TicketRequest("title","description");

        Exception exception = assertThrows(Exception.class, () -> {
            mockMvc.perform(put("/api/account/tickets/{id}", ticketId)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
        });

        assertInstanceOf(UnsupportedOperationException.class, exception.getCause());
        assertEquals("Update endpoint not implemented yet for ID: 123", exception.getCause().getMessage());
    }
}

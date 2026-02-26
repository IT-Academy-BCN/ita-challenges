package com.itachallenges.challengeservice.catalog.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.ChallengeApplication;
import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeCommand;
import com.itachallenges.challengeservice.catalog.application.dto.CreateChallengeResult;
import com.itachallenges.challengeservice.catalog.domain.port.in.CreateChallengeUseCase;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ChallengeController.class)
@ContextConfiguration(classes = ChallengeApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class ChallengeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateChallengeUseCase createChallengeUseCase;

    @Test
    void shouldCreateChallenge() throws Exception {
        when(createChallengeUseCase.create(any(CreateChallengeCommand.class)))
                .thenReturn(new CreateChallengeResult(ChallengeId.from("00000000-0000-0000-0000-000000000001")));

        mockMvc.perform(post("/api/v1/challenges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"T1","description":"D1"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("00000000-0000-0000-0000-000000000001"));

        var captor = ArgumentCaptor.forClass(CreateChallengeCommand.class);
        verify(createChallengeUseCase).create(captor.capture());
        assertEquals("T1", captor.getValue().title());
        assertEquals("D1", captor.getValue().description());

        verifyNoMoreInteractions(createChallengeUseCase);
    }

    @Test
    void shouldReturn400WhenInvalidRequest() throws Exception {
        mockMvc.perform(post("/api/v1/challenges")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title":"","description":""}
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(createChallengeUseCase);
    }
}

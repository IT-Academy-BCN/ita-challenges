package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.FinalizeSubmissionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubmissionController.class)
class SubmissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FinalizeSubmissionUseCase finalizeSubmissionUseCase;

    @Test
    void shouldFinalizeSubmissionSuccessfully() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                "line 1"
        );

        doNothing().when(finalizeSubmissionUseCase).execute(any());

        mockMvc.perform(post("/api/challenge/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldFinalizeWithEmptyCodeWhenCodeIsMissing() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                null
        );

        doNothing().when(finalizeSubmissionUseCase).execute(any());

        mockMvc.perform(post("/api/challenge/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn409WhenSubmissionAlreadySubmitted() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                "my solution"
        );

        doThrow(new IllegalStateException("Submission already submitted"))
                .when(finalizeSubmissionUseCase).execute(any());

        mockMvc.perform(post("/api/challenge/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn404WhenSubmissionNotFound() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                "my solution"
        );

        doThrow(new IllegalArgumentException("Submission not found"))
                .when(finalizeSubmissionUseCase).execute(any());

        mockMvc.perform(post("/api/challenge/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
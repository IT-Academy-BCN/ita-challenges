package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.submission.application.dto.FinalizeSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.in.MarkIncompleteUseCase;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.FinalizeSubmissionRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
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

    @MockBean
    private MarkIncompleteUseCase markIncompleteUseCase;

    @MockBean
    private SaveDraftSubmissionUseCase saveDraftSubmissionUseCase;

    @Test
    void shouldFinalizeSubmissionSuccessfully() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                List.of("line 1", "line 2")
        );

        mockMvc.perform(post("/api/v1/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        ArgumentCaptor<FinalizeSubmissionCommand> captor =
                ArgumentCaptor.forClass(FinalizeSubmissionCommand.class);
        verify(finalizeSubmissionUseCase).execute(captor.capture());

        assertThat(captor.getValue().challengeId()).isEqualTo("00000000-0000-0000-0000-000000000001");
        assertThat(captor.getValue().userId()).isEqualTo("00000000-0000-0000-0000-000000000002");
        assertThat(captor.getValue().content()).containsExactly("line 1", "line 2");
    }

    @Test
    void shouldFinalizeSubmissionWithEmptyContentWhenContentIsMissing() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                null
        );

        mockMvc.perform(post("/api/v1/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        ArgumentCaptor<FinalizeSubmissionCommand> captor =
                ArgumentCaptor.forClass(FinalizeSubmissionCommand.class);
        verify(finalizeSubmissionUseCase).execute(captor.capture());

        assertThat(captor.getValue().content()).isEmpty();
    }

    @Test
    void shouldReturn409WhenSubmissionAlreadySubmitted() throws Exception {
        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                "00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                List.of("my solution")
        );

        doThrow(new IllegalStateException("A submitted answer already exists"))
                .when(finalizeSubmissionUseCase).execute(any());

        mockMvc.perform(post("/api/v1/submissions/finalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
}
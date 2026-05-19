package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SaveDraftSubmissionRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubmissionController.class)
class SubmissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SaveDraftSubmissionUseCase saveDraftSubmissionUseCase;

    @MockBean
    private SubmissionRepository submissionRepository;

    @Test
    void shouldSubmitSuccessfully() throws Exception {
        SaveDraftSubmissionRequest request = new SaveDraftSubmissionRequest(
                "challenge-1", "student-1", "my solution"
        );

        mockMvc.perform(post("/api/challenge/submissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        ArgumentCaptor<SaveDraftSubmissionCommand> captor =
                ArgumentCaptor.forClass(SaveDraftSubmissionCommand.class);
        verify(saveDraftSubmissionUseCase).execute(captor.capture());
        assertThat(captor.getValue().challengeId()).isEqualTo("challenge-1");
        assertThat(captor.getValue().userId()).isEqualTo("student-1");
        assertThat(captor.getValue().code()).isEqualTo("my solution");
    }

    @Test
    void shouldSubmitWithEmptyCodeWhenCodeIsMissing() throws Exception {
        SaveDraftSubmissionRequest request = new SaveDraftSubmissionRequest(
                "challenge-1", "student-1", null
        );

        mockMvc.perform(post("/api/challenge/submissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        ArgumentCaptor<SaveDraftSubmissionCommand> captor =
                ArgumentCaptor.forClass(SaveDraftSubmissionCommand.class);
        verify(saveDraftSubmissionUseCase).execute(captor.capture());
        assertThat(captor.getValue().code()).isEmpty();
    }

    @Test
    void shouldSaveDraftWithCodeWhenCodeIsProvided() throws Exception {

        SaveDraftSubmissionRequest request = new SaveDraftSubmissionRequest(
                "challenge-1",
                "student-1",
                "console.log('hello world in a happy path')"
        );

        mockMvc.perform(post("/api/challenge/submissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        ArgumentCaptor<SaveDraftSubmissionCommand> captor =
                ArgumentCaptor.forClass(SaveDraftSubmissionCommand.class);

        verify(saveDraftSubmissionUseCase).execute(captor.capture());

        assertThat(captor.getValue().challengeId()).isEqualTo("challenge-1");
        assertThat(captor.getValue().userId()).isEqualTo("student-1");
        assertThat(captor.getValue().code()).isEqualTo("console.log('hello world in a happy path')");
    }

}

package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeLanguage;
import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.FinalizeSubmissionRequest;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SaveDraftSubmissionRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @MockBean
    private ChallengeRepository challengeRepository;

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

    @Test
    void existsFinalSubmission_ShouldReturnOkWithFalseStatus_WhenSubmissionNotExisting() throws Exception {
        when(submissionRepository.existsFinalSubmission(any(), any())).thenReturn(false);

        mockMvc.perform(get("/api/challenge/submissions/finalized")
                        .param("userId", "11111111-1111-1111-1111-111111111111")
                        .param("challengeId", "11111111-1111-1111-1111-111111111111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exists").value(false));
    }
    @Test
    void shouldFinalizeSubmissionSuccessfully() throws Exception {
        String challengeId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        Challenge mockChallenge = Challenge.restore(
                ChallengeId.of(challengeId),
                "Challenge Title",
                "Description",
                ChallengeLanguage.JAVASCRIPT,
                ChallengeDifficulty.EASY,
                "official solution"
        );

        FinalizeSubmissionRequest request = new FinalizeSubmissionRequest(
                challengeId, userId, "my solution", true);

        when(challengeRepository.find(any(ChallengeId.class))).thenReturn(mockChallenge);
        when(submissionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/challenge/submissions/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.submissionId").exists())
                .andExpect(jsonPath("$.studentCode").value("my solution"))
                .andExpect(jsonPath("$.challengeTitle").value("Challenge Title"))
                .andExpect(jsonPath("$.officialSolution").value("official solution"))
                .andExpect(jsonPath("$.revealedSolution").value(true));
    }


    @Test
    void existsFinalSubmission_ShouldReturnOkWithTrueStatus_WhenSubmissionExisting() throws Exception {
        when(submissionRepository.existsFinalSubmission(any(), any())).thenReturn(true);

        mockMvc.perform(get("/api/challenge/submissions/finalized")
                        .param("userId", "11111111-1111-1111-1111-111111111111")
                        .param("challengeId", "11111111-1111-1111-1111-111111111111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exists").value(true));
    }
}

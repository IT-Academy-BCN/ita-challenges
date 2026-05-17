package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.controller;

import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionStatus;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto.SaveDraftSubmissionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubmissionControllerTest {

    @Mock
    private SubmissionRepository repository;

    private SubmissionController controller;
    @BeforeEach
    void setUp() {
        controller = new SubmissionController(repository);
        when(repository.save(any(Submission.class))).thenAnswer(i -> i.getArgument(0));
    }


    @Test
    void shouldFinalizeSubmission() {
        // TODO: implement
    }

    @Test
    void shouldMarkSubmissionAsIncomplete() {
        // TODO: implement
    }

    @Test
    void shouldSaveDraftSubmission() {
        SaveDraftSubmissionRequest request = new SaveDraftSubmissionRequest("public class Solution {}");

        ResponseEntity<Void> response = controller.saveDraft(
                "550e8400-e29b-41d4-a716-446655440000",
                "660e8400-e29b-41d4-a716-446655440000",
                request
        );

        assertThat(response.getStatusCode().value()).isEqualTo(201);
    }


    @Test
    void shouldSaveDraftWithInProgressStatus() {
        SaveDraftSubmissionRequest request = new SaveDraftSubmissionRequest("public class Solution {}");

        controller.saveDraft(
                "550e8400-e29b-41d4-a716-446655440000",
                "660e8400-e29b-41d4-a716-446655440000",
                request
        );

        verify(repository).save(argThat(submission ->
                submission.getStatus() == SubmissionStatus.IN_PROGRESS
        ));
    }

    @Test
    void shouldReturn400WhenInvalidRequest() {
        // TODO: implement
    }
}

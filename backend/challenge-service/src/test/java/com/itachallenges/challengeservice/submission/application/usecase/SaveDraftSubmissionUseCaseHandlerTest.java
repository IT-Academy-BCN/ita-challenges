package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SaveDraftSubmissionUseCaseHandlerTest {

    @Mock
    private SubmissionRepository repository;

    private SaveDraftSubmissionUseCaseHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SaveDraftSubmissionUseCaseHandler(repository);
    }



    @Test
    void shouldSaveDraftSubmission() {
        SaveDraftSubmissionCommand command = new SaveDraftSubmissionCommand(
                "550e8400-e29b-41d4-a716-446655440000",
                "660e8400-e29b-41d4-a716-446655440000",
                "public class Solution {}"
        );

        when(repository.save(any(Submission.class))).thenAnswer(i -> i.getArgument(0));

        handler.execute(command);

        verify(repository).save(any(Submission.class));
    }


    @Test
    void shouldSaveDraftWithInProgressStatus() {
        SaveDraftSubmissionCommand command = new SaveDraftSubmissionCommand(
                "550e8400-e29b-41d4-a716-446655440000",
                "660e8400-e29b-41d4-a716-446655440000",
                "public class Solution {}"
        );

        when(repository.save(any(Submission.class))).thenAnswer(i -> i.getArgument(0));

        handler.execute(command);

        verify(repository).save(argThat(submission ->
                submission.getStatus() == SubmissionStatus.IN_PROGRESS
        ));
    }
    }

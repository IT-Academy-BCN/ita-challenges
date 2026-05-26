package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.application.dto.FinalizeSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class FinalizeSubmissionUseCaseHandlerTest {

    @Mock
    private SubmissionRepository repository;

    @InjectMocks
    private FinalizeSubmissionUseCaseHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFinalizeSubmission() {

        String challengeIdString = UUID.randomUUID().toString();
        String userIdString = UUID.randomUUID().toString();
        String code = "public class MySolution { /* code */ }";

        FinalizeSubmissionCommand command = new FinalizeSubmissionCommand(challengeIdString, userIdString, code);

        when(repository.findByUserAndChallenge(any(UserId.class), any(ChallengeId.class)))
                .thenReturn(Optional.empty()); // No previous submission

        handler.execute(command);

        ArgumentCaptor<Submission> submissionCaptor = ArgumentCaptor.forClass(Submission.class);
        verify(repository, times(1)).save(submissionCaptor.capture());

        Submission capturedSubmission = submissionCaptor.getValue();
        assertThat(capturedSubmission.getId()).isNotNull();
        assertThat(capturedSubmission.getChallengeId()).isEqualTo(new ChallengeId(UUID.fromString(challengeIdString)));
        assertThat(capturedSubmission.getUserId()).isEqualTo(new UserId(UUID.fromString(userIdString)));
        assertThat(capturedSubmission.getCode()).isEqualTo(code);
        assertThat(capturedSubmission.getCreatedAt()).isNotNull();


        verify(repository, times(1)).findByUserAndChallenge(
                new UserId(UUID.fromString(userIdString)),
                new ChallengeId(UUID.fromString(challengeIdString))
        );
    }

    @Test
    void shouldThrowExceptionWhenSubmissionAlreadyFinal() {
        String challengeIdString = UUID.randomUUID().toString();
        String userIdString = UUID.randomUUID().toString();
        String code = "public class MySolution { /* code */ }";

        FinalizeSubmissionCommand command = new FinalizeSubmissionCommand(challengeIdString, userIdString, code);


        when(repository.findByUserAndChallenge(any(UserId.class), any(ChallengeId.class)))
                .thenReturn(Optional.of(mock(Submission.class)));
        when(repository.existsFinalSubmission(any(UserId.class), any(ChallengeId.class)))
                .thenReturn(true);


        assertThatThrownBy(() -> handler.execute(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Challenge was submitted before by User:" + userIdString);

        verify(repository, never()).save(any(Submission.class));
        verify(repository, times(1)).findByUserAndChallenge(
                new UserId(UUID.fromString(userIdString)),
                new ChallengeId(UUID.fromString(challengeIdString))
        );
    }
}
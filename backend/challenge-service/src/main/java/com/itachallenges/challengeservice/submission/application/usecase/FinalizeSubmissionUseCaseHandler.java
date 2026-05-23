package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.application.dto.FinalizeSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FinalizeSubmissionUseCaseHandler implements FinalizeSubmissionUseCase {

    private final SubmissionRepository repository;

    @Override
    public void execute(FinalizeSubmissionCommand command) {
        ChallengeId challengeId = new ChallengeId(UUID.fromString(command.challengeId()));
        UserId userId = new UserId(UUID.fromString(command.userId()));

        Optional<Submission> existingSubmission = repository.findByUserAndChallenge(userId, challengeId);
        if (existingSubmission.isPresent()) {
            throw new RuntimeException("Challenge was submited before by User:" + command.userId());
        }

        Submission submission = Submission.createSubmitted(
                SubmissionId.generate(),
                challengeId,
                userId,
                command.code()
        );

        repository.save(submission);
    }
}

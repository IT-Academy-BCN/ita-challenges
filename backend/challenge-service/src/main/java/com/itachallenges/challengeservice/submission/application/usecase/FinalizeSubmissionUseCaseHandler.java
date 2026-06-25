package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.application.dto.FinalizeSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.in.FinalizeSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.out.buffer.SubmissionBuffer;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FinalizeSubmissionUseCaseHandler implements FinalizeSubmissionUseCase {

    private final SubmissionRepository repository;

    public FinalizeSubmissionUseCaseHandler(SubmissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(FinalizeSubmissionCommand command) {
        var userId = UserId.of(command.userId());
        var challengeId = ChallengeId.of(command.challengeId());

        var draft = SubmissionBuffer.findLastByUserId(userId)
                .filter(submission -> submission.getChallengeId().equals(challengeId))
                .orElseThrow(() -> new NoSuchElementException(
                        "No active draft found for user: " + userId + " or challenge mismatch"
                ));

        Optional.ofNullable(command.code()).ifPresent(draft::updateCode);

        repository.save(Submission.toSubmitted(draft));
        SubmissionBuffer.removeByUserId(userId);
    }
}

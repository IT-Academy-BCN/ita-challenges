package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.application.dto.MarkIncompleteCommand;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.in.MarkIncompleteUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.out.buffer.SubmissionBuffer;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MarkIncompleteUseCaseHandler implements MarkIncompleteUseCase {

    private final SubmissionRepository repository;

    public MarkIncompleteUseCaseHandler(SubmissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(MarkIncompleteCommand command) {
        var submissionId = SubmissionId.of(command.submissionId());
        var userId = UserId.of(command.userId());

        var found = repository.findById(submissionId)
                .filter(submission -> submission.getUserId().equals(userId))
                .orElseThrow(() -> new NoSuchElementException(
                        "Submission not found with id: " + submissionId + " or does not belong to user"
                ));

        var incomplete = Submission.toInProgress(found);
        SubmissionBuffer.save(userId, incomplete);
        repository.save(incomplete);
    }
}

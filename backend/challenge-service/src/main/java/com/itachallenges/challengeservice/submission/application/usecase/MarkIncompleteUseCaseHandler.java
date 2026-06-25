package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.submission.application.dto.MarkIncompleteCommand;
import com.itachallenges.challengeservice.submission.domain.port.in.MarkIncompleteUseCase;
import org.springframework.stereotype.Service;

@Service
public class MarkIncompleteUseCaseHandler implements MarkIncompleteUseCase {
    private final SubmissionRepository repository;

    public MarkIncompleteUseCaseHandler(SubmissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(MarkIncompleteCommand command) {
        SubmissionId submissionId = SubmissionId.of(command.submissionId());
        UserId userId = UserId.of(command.userId());

        // Buscar la submission en el repositorio histórico (por ID)
        Optional<Submission> existing = repository.findById(submissionId);
        if (existing.isEmpty()) {
            throw new NoSuchElementException("Submission not found with id: " + submissionId);
        }

        Submission found = existing.get();
        if (!found.getUserId().equals(userId)) {
            throw new IllegalStateException("Submission does not belong to the given user");
        }

        Submission incomplete = Submission.toInProgress(found);
        SubmissionBuffer.save(userId, incomplete);
        repository.save(incomplete);
    }
}

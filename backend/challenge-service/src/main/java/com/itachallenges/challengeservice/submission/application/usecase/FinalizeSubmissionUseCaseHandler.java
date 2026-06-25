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
        UserId userId = UserId.of(command.userId());
        ChallengeId challengeId = ChallengeId.of(command.challengeId());

        // Buscar el draft activo del usuario en el buffer
        Optional<Submission> activeDraft = SubmissionBuffer.findLastByUserId(userId);
        if (activeDraft.isEmpty()) {
            throw new NoSuchElementException("No active draft found for user: " + userId);
        }

        Submission draft = activeDraft.get();
        if (!draft.getChallengeId().equals(challengeId)) {
            throw new IllegalStateException(
                    "Active draft belongs to a different challenge than the one being finalized");
        }

        if (command.code() != null) {
            draft.updateCode(command.code());
        }
        Submission finalized = Submission.toSubmitted(draft);

        repository.save(finalized);
        SubmissionBuffer.removeByUserId(userId);
    }
}

package com.itachallenges.challengeservice.submission.application.usecase;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.application.dto.SaveDraftSubmissionCommand;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.port.in.SaveDraftSubmissionUseCase;
import com.itachallenges.challengeservice.submission.domain.port.out.SubmissionRepository;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import com.itachallenges.challengeservice.submission.infrastructure.adapter.out.buffer.SubmissionBuffer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaveDraftSubmissionUseCaseHandler implements SaveDraftSubmissionUseCase {

    private final SubmissionRepository repository;

    public SaveDraftSubmissionUseCaseHandler(SubmissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(SaveDraftSubmissionCommand command) {
        UserId userId = UserId.of(command.userId());
        ChallengeId challengeId = ChallengeId.of(command.challengeId());
        String code = command.code();
        Optional<Submission> existingDraft = SubmissionBuffer.findLastByUserId(userId);

        Submission submission;

        if (existingDraft.isPresent()) {
            submission = existingDraft.get();
            submission.updateCode(code);
        } else {
            submission = Submission.createInProgress(
                    SubmissionId.generate(),
                    challengeId,
                    userId,
                    code
            );
        }
        SubmissionBuffer.save(userId, submission);

        // Guardar en el repositorio histórico
        repository.save(submission);
    }
}
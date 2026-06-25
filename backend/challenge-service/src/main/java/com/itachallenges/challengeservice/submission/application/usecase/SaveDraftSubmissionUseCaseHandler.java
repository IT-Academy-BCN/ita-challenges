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

@Service
public class SaveDraftSubmissionUseCaseHandler implements SaveDraftSubmissionUseCase {

    private final SubmissionRepository repository;

    public SaveDraftSubmissionUseCaseHandler(SubmissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(SaveDraftSubmissionCommand command) {
        var userId = UserId.of(command.userId());
        var challengeId = ChallengeId.of(command.challengeId());
        var code = command.code();

        var submission = SubmissionBuffer.findLastByUserId(userId)
                .map(existing -> {
                    existing.updateCode(code);
                    return existing;
                })
                .orElseGet(() -> Submission.createInProgress(
                        SubmissionId.generate(),
                        challengeId,
                        userId,
                        code
                ));

        SubmissionBuffer.save(userId, submission);
        repository.save(submission);
    }
}

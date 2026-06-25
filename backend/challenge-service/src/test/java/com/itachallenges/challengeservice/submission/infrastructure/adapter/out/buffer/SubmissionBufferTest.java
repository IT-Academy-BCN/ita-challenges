package com.itachallenges.challengeservice.submission.infrastructure.adapter.out.buffer;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class SubmissionBufferTest {
    @Test
    void should_save_and_find_and_remove() {
        var userId = new UserId(UUID.fromString("660e8400-e29b-41d4-a716-446655440001"));
        var submission = Submission.createInProgress(SubmissionId.generate(), new ChallengeId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000")), userId, "code");
        SubmissionBuffer.save(userId, submission);
        assertThat(SubmissionBuffer.findLastByUserId(userId)).isPresent();
        SubmissionBuffer.removeByUserId(userId);
        assertThat(SubmissionBuffer.findLastByUserId(userId)).isEmpty();
    }
}

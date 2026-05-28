package com.itachallenges.challengeservice.submission.domain;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionId;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class SubmissionTest {

    private static final UUID CHALLENGE_UUID = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
    private static final UUID USER_UUID = UUID.fromString("660e8400-e29b-41d4-a716-446655440001");

    @Test
    void should_create_submission_in_progress_with_all_fields_populated() {
        SubmissionId submissionId = SubmissionId.generate();
        ChallengeId challengeId = new ChallengeId(CHALLENGE_UUID);
        UserId userId = new UserId(USER_UUID);
        String code = "System.out.println(\"Hello this is a submission in progress\");";

        Submission submission = Submission.createInProgress(submissionId, challengeId, userId, code);

        assertThat(submission.getId()).isEqualTo(submissionId);
        assertThat(submission.getChallengeId()).isEqualTo(challengeId);
        assertThat(submission.getUserId()).isEqualTo(userId);
        assertThat(submission.getCode()).isEqualTo(code);
        assertThat(submission.getStatus()).isEqualTo(SubmissionStatus.IN_PROGRESS);
        assertThat(submission.getCreatedAt()).isNotNull();
        assertThat(submission.getUpdatedAt()).isNotNull();
    }

    @Test
    void should_create_submission_with_all_fields_populated() {
        SubmissionId submissionId = SubmissionId.generate();
        ChallengeId challengeId = new ChallengeId(CHALLENGE_UUID);
        UserId userId = new UserId(USER_UUID);
        String code = "System.out.println(\"Hello this is a submission in progress\");";

        Submission submission = Submission.createSubmitted(submissionId, challengeId, userId, code);

        assertThat(submission.getId()).isEqualTo(submissionId);
        assertThat(submission.getChallengeId()).isEqualTo(challengeId);
        assertThat(submission.getUserId()).isEqualTo(userId);
        assertThat(submission.getCode()).isEqualTo(code);
        assertThat(submission.getStatus()).isEqualTo(SubmissionStatus.SUBMITTED);
        assertThat(submission.getCreatedAt()).isNotNull();
        assertThat(submission.getUpdatedAt()).isNotNull();

    }

}

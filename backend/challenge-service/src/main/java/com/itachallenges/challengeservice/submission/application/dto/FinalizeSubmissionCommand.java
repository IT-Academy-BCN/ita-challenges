package com.itachallenges.challengeservice.submission.application.dto;

public record FinalizeSubmissionCommand(
        String challengeId,
        String userId,
        String code
) {}

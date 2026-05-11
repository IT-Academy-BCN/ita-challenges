package com.itachallenges.challengeservice.submission.application.dto;

public record SaveDraftSubmissionCommand(
        String challengeId,
        String userId,
        String code
) {}

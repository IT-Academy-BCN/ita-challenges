package com.itachallenges.challengeservice.submission.application.dto;

public record MarkIncompleteCommand(
        String submissionId,
        String userId
) {}
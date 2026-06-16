package com.itachallenges.challengeservice.submission.infrastructure.adapter.in.web.dto;

public record SubmissionResultResponse(  String submissionId,
                                        String studentCode,
                                        String challengeTitle,
                                        String officialSolution,
                                        boolean revealedSolution) {
}
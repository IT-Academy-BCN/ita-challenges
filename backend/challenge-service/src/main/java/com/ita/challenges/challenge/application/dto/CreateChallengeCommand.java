package com.ita.challenges.challenge.application.dto;

public record CreateChallengeCommand(
        String title,
        String description
) {
}

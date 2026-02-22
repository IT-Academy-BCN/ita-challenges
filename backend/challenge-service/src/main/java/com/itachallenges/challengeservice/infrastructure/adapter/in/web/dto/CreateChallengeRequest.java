package com.itachallenges.challengeservice.infrastructure.adapter.in.web.dto;

import com.itachallenges.challengeservice.application.dto.CreateChallengeCommand;
import jakarta.validation.constraints.NotBlank;

public record CreateChallengeRequest(
        @NotBlank String title,
        @NotBlank String description
) {
    public CreateChallengeCommand toCommand() {
        return new CreateChallengeCommand(title, description);
    }
}

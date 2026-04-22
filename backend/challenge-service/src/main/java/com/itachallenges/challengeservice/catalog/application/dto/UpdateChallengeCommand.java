package com.itachallenges.challengeservice.catalog.application.dto;

import java.util.UUID;

public record UpdateChallengeCommand(UUID id, String title, String description) {}
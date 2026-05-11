package com.itachallenges.challengeservice.stat.domain.port.in;

import com.itachallenges.challengeservice.stat.application.dto.ChallengeStatsResponse;

public interface GetChallengeStatsUseCase {
    ChallengeStatsResponse execute(String challengeId);
}

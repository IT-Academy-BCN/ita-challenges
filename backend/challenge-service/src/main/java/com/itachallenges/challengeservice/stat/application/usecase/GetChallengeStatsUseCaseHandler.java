package com.itachallenges.challengeservice.stat.application.usecase;

import com.itachallenges.challengeservice.stat.application.dto.ChallengeStatsResponse;
import com.itachallenges.challengeservice.stat.domain.port.in.GetChallengeStatsUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetChallengeStatsUseCaseHandler implements GetChallengeStatsUseCase {
    // TODO: inject ChallengeStatsRepository

    @Override
    public ChallengeStatsResponse execute(String challengeId) {
        // TODO: load ChallengeStats by challengeId
        // TODO: return ChallengeStatsResponse
        return null;
    }
}
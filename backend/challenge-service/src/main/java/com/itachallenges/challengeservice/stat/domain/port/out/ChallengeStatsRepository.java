package com.itachallenges.challengeservice.stat.domain.port.out;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.stat.domain.ChallengeStats;

import java.util.Optional;

public interface ChallengeStatsRepository {
    ChallengeStats save(ChallengeStats stats);
    Optional<ChallengeStats> findByChallengeId(ChallengeId challengeId);
}
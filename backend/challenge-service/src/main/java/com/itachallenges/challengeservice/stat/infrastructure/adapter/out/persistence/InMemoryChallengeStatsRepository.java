package com.itachallenges.challengeservice.stat.infrastructure.adapter.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.stat.domain.ChallengeStats;
import com.itachallenges.challengeservice.stat.domain.port.out.ChallengeStatsRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryChallengeStatsRepository implements ChallengeStatsRepository {

    private final Map<ChallengeId, ChallengeStats> storage = new ConcurrentHashMap<>();

    @Override
    public ChallengeStats save(ChallengeStats stats) {
        storage.put(stats.getChallengeId(), stats);
        return stats;
    }

    @Override
    public Optional<ChallengeStats> findByChallengeId(ChallengeId challengeId) {
        // TODO: find ChallengeStats by challengeId
        return Optional.empty();
    }
}
package com.itachallenges.challengeservice.infrastructure.adapter.out.persistence.inmemory;

import com.itachallenges.challengeservice.domain.model.Challenge;
import com.itachallenges.challengeservice.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.domain.valueobject.ChallengeId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryChallengeRepository implements ChallengeRepository {

    private final Map<ChallengeId, Challenge> storage = new ConcurrentHashMap<>();

    @Override
    public Challenge save(Challenge challenge) {
        storage.put(challenge.id(), challenge);
        return challenge;
    }

    @Override
    public Optional<Challenge> findById(ChallengeId id) {
        return Optional.ofNullable(storage.get(id));
    }
}
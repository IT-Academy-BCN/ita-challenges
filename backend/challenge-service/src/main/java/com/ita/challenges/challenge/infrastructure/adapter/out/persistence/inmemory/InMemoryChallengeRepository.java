package com.ita.challenges.challenge.infrastructure.adapter.out.persistence.inmemory;

import com.ita.challenges.challenge.domain.model.Challenge;
import com.ita.challenges.challenge.domain.port.out.ChallengeRepository;
import com.ita.challenges.challenge.domain.valueobject.ChallengeId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
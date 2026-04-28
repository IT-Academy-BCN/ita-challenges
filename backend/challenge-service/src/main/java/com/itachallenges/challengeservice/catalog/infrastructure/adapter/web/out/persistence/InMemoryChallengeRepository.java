package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class InMemoryChallengeRepository implements ChallengeRepository {
  
    private final Map<ChallengeId, Challenge> storage = new ConcurrentHashMap<>();
    
    @Override
    public Challenge save(Challenge challenge) {
        storage.put(challenge.getId(), challenge);
        return challenge;
    }
    
    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
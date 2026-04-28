package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Repository
public class InMemoryChallengeRepository implements ChallengeRepository {

    Map<ChallengeId, Challenge> storage = new ConcurrentHashMap<>();
    
    @Override
    public Challenge save(Challenge challenge) {
        storage.put(challenge.getId(), challenge);
        return challenge;
    }
    
    @Override
    public List<Challenge> findAll() {
        throw new UnsupportedOperationException("findAll not implemented yet"); 
    }
    
    @Override
    public Challenge update(Challenge challenge) {
        ChallengeId id = challenge.getId();
        if (!storage.containsKey(id)) {
            throw new RuntimeException("Challenge not found with id: " + id);
        }
        
        storage.put(id, challenge);
        return challenge;
    }
    
    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

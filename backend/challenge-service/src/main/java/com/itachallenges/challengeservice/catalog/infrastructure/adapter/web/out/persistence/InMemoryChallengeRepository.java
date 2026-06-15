package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import java.util.ArrayList;

@Repository
public class InMemoryChallengeRepository implements ChallengeRepository {

    Map<ChallengeId, Challenge> storage = new ConcurrentHashMap<>();

    @Override
    public Challenge save(Challenge challenge) {
        try{
            storage.put(challenge.getId(), challenge);
            return challenge;
        }catch(RuntimeException e){ throw new RuntimeException("Error saving challenge" + e.getMessage());}

    }

    @Override
    public Challenge update(Challenge challenge) {
        ChallengeId id = challenge.getId();
        ensureChallengeExists(id);


        storage.put(id, challenge);
        return challenge;
    }

    @Override
    public void delete(ChallengeId id) {
        ensureChallengeExists(id);
        storage.remove(id);
    }

    @Override
    public Challenge find(ChallengeId id) {
        Challenge challenge = storage.get(id);
        ensureChallengeExists(id);

        return challenge;
    }

    @Override
    public List<Challenge> findAll() {
        return new ArrayList<>(storage.values());
    }

    private void ensureChallengeExists(ChallengeId id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchElementException("Challenge not found with id: " + id);
        }
    }


}
package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import org.springframework.stereotype.Repository;


@Repository
public class InMemoryChallengeRepository implements ChallengeRepository {

    @Override
    public Challenge update(Challenge challenge) {
        throw new UnsupportedOperationException("To be implemented");
    }

    @Override
    public Challenge save(Challenge challenge) {
        throw new UnsupportedOperationException("To be implemented");
    }
    
    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
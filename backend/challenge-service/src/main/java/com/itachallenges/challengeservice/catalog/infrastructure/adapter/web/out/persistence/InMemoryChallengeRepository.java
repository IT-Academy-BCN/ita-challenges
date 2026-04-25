package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class InMemoryChallengeRepository implements ChallengeRepository {

    @Override
    public List<Challenge> findAll() {
        throw new UnsupportedOperationException("findAll not implemented yet");
    }
}

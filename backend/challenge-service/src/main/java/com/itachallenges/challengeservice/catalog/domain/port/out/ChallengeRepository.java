package com.itachallenges.challengeservice.catalog.domain.port.out;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;

import java.util.List;

public interface ChallengeRepository {

    List<Challenge> findAll();
    Challenge update(Challenge challenge);
    Challenge save(Challenge challenge);
    void delete(ChallengeId id);
    Challenge find(ChallengeId id);
}

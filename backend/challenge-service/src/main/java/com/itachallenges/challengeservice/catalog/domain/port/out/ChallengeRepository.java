package com.itachallenges.challengeservice.catalog.domain.port.out;

import com.itachallenges.challengeservice.catalog.domain.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;

import java.util.Optional;

public interface ChallengeRepository {

    Challenge save(Challenge challenge);

    Optional<Challenge> findById(ChallengeId id);
}
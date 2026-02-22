package com.itachallenges.challengeservice.domain.port.out;

import com.itachallenges.challengeservice.domain.model.Challenge;
import com.itachallenges.challengeservice.domain.valueobject.ChallengeId;

import java.util.Optional;

public interface ChallengeRepository {

    Challenge save(Challenge challenge);

    Optional<Challenge> findById(ChallengeId id);
}
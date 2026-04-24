package com.itachallenges.challengeservice.catalog.domain.port.out;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;

public interface ChallengeRepository {
    Challenge create(Challenge challenge);
}
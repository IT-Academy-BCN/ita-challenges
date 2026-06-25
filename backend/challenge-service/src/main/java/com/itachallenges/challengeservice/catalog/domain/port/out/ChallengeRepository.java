package com.itachallenges.challengeservice.catalog.domain.port.out;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeLanguage;

import java.util.List;

public interface ChallengeRepository {

    List<Challenge> findAll();
    List<Challenge> findByLanguage(ChallengeLanguage language);
    Challenge update(Challenge challenge);
    Challenge save(Challenge challenge);
    void delete(ChallengeId id);
    Challenge find(ChallengeId id);
}

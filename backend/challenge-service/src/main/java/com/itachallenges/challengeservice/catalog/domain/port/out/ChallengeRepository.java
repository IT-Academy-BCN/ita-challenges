package com.itachallenges.challengeservice.catalog.domain.port.out;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import java.util.List;

public interface ChallengeRepository {

    Challenge update(Challenge challenge);
    List<Challenge> findAll();
    Challenge update(Challenge challenge);
    Challenge save(Challenge challenge);
    void delete(String id);

}

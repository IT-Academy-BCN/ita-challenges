package com.ita.challenges.challenge.domain.port.out;

import com.ita.challenges.challenge.domain.model.Challenge;
import com.ita.challenges.challenge.domain.valueobject.ChallengeId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeRepository {

    Challenge save(Challenge challenge);

    Optional<Challenge> findById(ChallengeId id);
}
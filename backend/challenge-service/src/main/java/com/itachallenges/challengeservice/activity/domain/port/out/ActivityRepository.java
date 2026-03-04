package com.itachallenges.challengeservice.activity.domain.port.out;

import com.itachallenges.challengeservice.activity.domain.Activity;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;

import java.util.Optional;

public interface ActivityRepository {
    Activity save(Activity activity);
    Optional<Activity> findByUserIdAndChallengeId(UserId userId, ChallengeId challengeId);
}
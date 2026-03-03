package com.itachallenges.challengeservice.activity.infrastructure.adapter.out.persistence;

import com.itachallenges.challengeservice.activity.domain.Activity;
import com.itachallenges.challengeservice.activity.domain.port.out.ActivityRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryActivityRepository implements ActivityRepository {

    private final Map<String, Activity> storage = new ConcurrentHashMap<>();

    @Override
    public Activity save(Activity activity) {
        String key = activity.getUserId().toString() + ":" + activity.getChallengeId().toString();
        storage.put(key, activity);
        return activity;
    }

    @Override
    public Optional<Activity> findByUserIdAndChallengeId(UserId userId, ChallengeId challengeId) {
        // TODO: find UserActivity by userId and challengeId
        return Optional.empty();
    }
}

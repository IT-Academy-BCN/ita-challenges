package com.itachallenges.challengeservice.activity.domain;

import com.itachallenges.challengeservice.activity.domain.valueobject.ActivityId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;

import java.util.ArrayList;
import java.util.List;

public class Activity {

    private ActivityId id;
    private UserId userId;
    private ChallengeId challengeId;
    private boolean favorite;
    private boolean bookmark;

    private final List<Object> domainEvents = new ArrayList<>();

    private Activity() {}

    public static Activity create(ActivityId id, UserId userId, ChallengeId challengeId) {
        // TODO: create and return a new UserActivity with favorite and bookmark set to false
        return null;
    }

    public void toggleFavorite() {
        // TODO: toggle favorite and emit FavoriteAdded or FavoriteRemoved event
    }

    public void toggleBookmark() {
        // TODO: toggle bookmark and emit BookmarkAdded or BookmarkRemoved event
    }

    public List<Object> pullDomainEvents() {
        // TODO: return accumulated domain events and clear the list
        return null;
    }

    public ActivityId getId() { return id; }
    public UserId getUserId() { return userId; }
    public ChallengeId getChallengeId() { return challengeId; }
    public boolean isFavorite() { return favorite; }
    public boolean isBookmark() { return bookmark; }
}
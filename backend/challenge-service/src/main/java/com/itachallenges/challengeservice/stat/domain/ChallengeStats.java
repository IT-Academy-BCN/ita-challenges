package com.itachallenges.challengeservice.stat.domain;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.stat.domain.valueobject.ChallengeStatsId;

public class ChallengeStats {

    private ChallengeStatsId id;
    private ChallengeId challengeId;
    private long timesDone;
    private long favorites;
    private long bookmarks;

    private ChallengeStats() {}

    public static ChallengeStats create(ChallengeStatsId id, ChallengeId challengeId) {
        // TODO: create and return a new ChallengeStats with all counters set to 0
        return null;
    }

    public void incrementTimesDone() {
        // TODO: increment timesDone counter
    }

    public void incrementFavorites() {
        // TODO: increment favorites counter
    }

    public void decrementFavorites() {
        // TODO: decrement favorites counter, never below 0
    }

    public void incrementBookmarks() {
        // TODO: increment bookmarks counter
    }

    public void decrementBookmarks() {
        // TODO: decrement bookmarks counter, never below 0
    }

    public ChallengeStatsId getId() { return id; }
    public ChallengeId getChallengeId() { return challengeId; }
    public long getTimesDone() { return timesDone; }
    public long getFavorites() { return favorites; }
    public long getBookmarks() { return bookmarks; }
}

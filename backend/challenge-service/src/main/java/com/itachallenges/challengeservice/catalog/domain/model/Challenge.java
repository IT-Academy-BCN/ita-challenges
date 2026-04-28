package com.itachallenges.challengeservice.catalog.domain.model;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDescription;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeTitle;
import lombok.Getter;

@Getter
public class Challenge {

    private final ChallengeId id;
    private final ChallengeTitle title;
    private final ChallengeDescription description;

    private Challenge(ChallengeId id, ChallengeTitle title, ChallengeDescription description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static Challenge create(String title, String description) {
        return new Challenge(
                ChallengeId.generate(),
                new ChallengeTitle(title),
                new ChallengeDescription(description)
        );
    }

    public static Challenge restore(ChallengeId id, String title, String description) {
        return new Challenge(
                id,
                new ChallengeTitle(title),
                new ChallengeDescription(description)
        );
    }
}

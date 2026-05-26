package com.itachallenges.challengeservice.catalog.domain.model;

import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDescription;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeSolution;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeTitle;
import lombok.Getter;

@Getter
public class Challenge {

    private final ChallengeId id;
    private final ChallengeTitle title;
    private final ChallengeDescription description;
    private final ChallengeDifficulty difficulty;
    private final ChallengeSolution solution;

    private Challenge(ChallengeId id, ChallengeTitle title, ChallengeDescription description, ChallengeDifficulty difficulty, ChallengeSolution solution) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.solution = solution;
    }

    public static Challenge create(String title, String description, ChallengeDifficulty difficulty, String solution) {
        return new Challenge(
                ChallengeId.generate(),
                new ChallengeTitle(title),
                new ChallengeDescription(description),
                difficulty,
                new ChallengeSolution(solution)
        );
    }

    public static Challenge restore(ChallengeId id, String title, String description, ChallengeDifficulty difficulty, String solution) {
        return new Challenge(
                id,
                new ChallengeTitle(title),
                new ChallengeDescription(description),
                difficulty,
                new ChallengeSolution(solution)
        );
    }
}

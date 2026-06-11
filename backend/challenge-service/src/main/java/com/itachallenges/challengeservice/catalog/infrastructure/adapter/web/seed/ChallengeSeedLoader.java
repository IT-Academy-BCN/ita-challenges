package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.seed;

import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeLanguage;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ChallengeSeedLoader implements ApplicationRunner {
    private final ChallengeRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (repository.findAll().isEmpty()) {

            List<Challenge> initialChallenges = List.of(
                    Challenge.restore(
                            ChallengeId.of("11111111-1111-1111-1111-111111111111"),
                            "FizzBuzz",
                            "Create a FizzBuzz implementation.",
                            ChallengeLanguage.JAVA,
                            ChallengeDifficulty.EASY,
                            "for(int i=1;i<=100;i++){System.out.println(i%15==0?\"FizzBuzz\":i%3==0?\"Fizz\":i%5==0?\"Buzz\":i);}"
                    ),
                    Challenge.restore(
                            ChallengeId.of("22222222-2222-2222-2222-222222222222"),
                            "Palindrome Checker",
                            "Check if a text is palindrome.",
                            ChallengeLanguage.JAVASCRIPT,
                            ChallengeDifficulty.MEDIUM,
                            "const isPalindrome = str => str === str.split('').reverse().join('');"
                    ),
                    Challenge.restore(
                            ChallengeId.of("33333333-3333-3333-3333-333333333333"),
                            "REST API Basics",
                            "Build a simple CRUD REST API.",
                            ChallengeLanguage.TYPESCRIPT,
                            ChallengeDifficulty.HARD,
                            "app.get('/items', (req, res) => res.json(db));"
                    )
            );

            initialChallenges.forEach(repository::save);
        }
    }
}

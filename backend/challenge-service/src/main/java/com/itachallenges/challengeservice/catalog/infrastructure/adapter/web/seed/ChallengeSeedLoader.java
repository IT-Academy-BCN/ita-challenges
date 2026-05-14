package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.seed;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.InputStream;
import java.util.List;

@Component
public class ChallengeSeedLoader implements ApplicationRunner {
    private static final String SEED_FILE = "challenges-seed.json";
    private final ChallengeRepository repository;
    private final ObjectMapper objectMapper;

    public ChallengeSeedLoader(ChallengeRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ClassPathResource resource = new ClassPathResource(SEED_FILE);
        try (InputStream inputStream = resource.getInputStream()) {
            List<ChallengeSeed> seeds = objectMapper.readValue(inputStream, new TypeReference<>() {});
            seeds.stream()
                    .map(seed -> Challenge.restore(
                            ChallengeId.of(seed.id()),
                            seed.title(),
                            seed.description()
                    ))
                    .forEach(repository::save);
        }
    }

    private record ChallengeSeed(String id, String title, String description) {}
}

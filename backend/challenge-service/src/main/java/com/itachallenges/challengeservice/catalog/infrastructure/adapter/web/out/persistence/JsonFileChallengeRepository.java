package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeDifficulty;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeLanguage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Repository
public class JsonFileChallengeRepository implements ChallengeRepository {

    private final File storageFile;
    private final ObjectMapper objectMapper;
    private final Map<ChallengeId, Challenge> storage = new ConcurrentHashMap<>();

    public JsonFileChallengeRepository(
            @Value("${challenge.storage.file-path:challenges.json}") String filePath,
            ObjectMapper objectMapper) {

        this.storageFile = new File(filePath);
        this.objectMapper = objectMapper;

        try {
            loadFromFile();
        } catch (IOException ignored) {
        }
    }

    @Override
    public Challenge save(Challenge challenge) {
        storage.put(challenge.getId(), challenge);

        try {
            persistToFile();
        } catch (IOException ignored) {
        }

        return challenge;
    }

    @Override
    public Challenge find(ChallengeId id) {
        return storage.get(id);
    }

    @Override
    public Challenge update(Challenge challenge) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(ChallengeId id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Challenge> findAll() {
        return new ArrayList<>(storage.values());
    }

    private void persistToFile() throws IOException {
        objectMapper.writeValue(storageFile, storage.values().stream()
                .map(c -> new ChallengeRecord(
                        c.getId().toString(),
                        c.getTitle().toString(),
                        c.getDescription().toString(),
                        c.getLanguage().toString(),
                        c.getDifficulty().toString(),
                        c.getSolution().toString()
                )).toList());
    }

    private void loadFromFile() throws IOException {
        if (!storageFile.exists()) return;

        objectMapper.readValue(storageFile, new TypeReference<List<ChallengeRecord>>() {
                })
                .forEach(r -> storage.put(
                        ChallengeId.of(r.id()),
                        Challenge.restore(
                                ChallengeId.of(r.id()),
                                r.title(),
                                r.description(),
                                ChallengeLanguage.valueOf(r.language()),
                                ChallengeDifficulty.valueOf(r.difficulty()),
                                (r.solution() != null && !r.solution().isBlank() ? r.solution() : "No solution provided")
                        )
                ));
    }

    record ChallengeRecord(String id, String title, String description, String language, String difficulty,
                           String solution) {
    }
}

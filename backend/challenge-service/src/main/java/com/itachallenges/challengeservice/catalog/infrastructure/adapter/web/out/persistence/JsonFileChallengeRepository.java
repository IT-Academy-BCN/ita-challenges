package com.itachallenges.challengeservice.catalog.infrastructure.adapter.web.out.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itachallenges.challengeservice.catalog.domain.model.Challenge;
import com.itachallenges.challengeservice.catalog.domain.port.out.ChallengeRepository;
import com.itachallenges.challengeservice.catalog.domain.valueobject.ChallengeId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.File;
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
        loadFromFile();
    }

    @Override
    public Challenge save(Challenge challenge) {
        storage.put(challenge.getId(), challenge);
        persistToFile();
        return challenge;
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

    private void persistToFile() throws Exception {
        List<ChallengeRecord> records = storage.values().stream()
                .map(c -> new ChallengeRecord(
                        c.getId().toString(),
                        c.getTitle().toString(),
                        c.getDescription().toString()
                ))
                .toList();
        objectMapper.writeValue(storageFile, records);
    }

    private void loadFromFile() throws Exception {
        if (!storageFile.exists()) return;
        List<ChallengeRecord> records = objectMapper.readValue(
                storageFile, new TypeReference<>() {});
        records.forEach(r -> {
            Challenge challenge = Challenge.restore(
                    ChallengeId.of(r.id()),
                    r.title(),
                    r.description()
            );
            storage.put(challenge.getId(), challenge);
        });
    }

    record ChallengeRecord(String id, String title, String description) {}
}

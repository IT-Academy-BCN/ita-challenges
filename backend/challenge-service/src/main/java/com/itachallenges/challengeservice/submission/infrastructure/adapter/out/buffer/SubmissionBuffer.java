package com.itachallenges.challengeservice.submission.infrastructure.adapter.out.buffer;

import com.itachallenges.challengeservice.shared.domain.valueobject.UserId;
import com.itachallenges.challengeservice.submission.domain.Submission;
import com.itachallenges.challengeservice.submission.domain.valueobject.SubmissionStatus;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class SubmissionBuffer {

    private static final Map<UserId, Submission> BUFFER = new ConcurrentHashMap<>();
    private SubmissionBuffer() {
        throw new UnsupportedOperationException("This is a utility/buffer class and should not be instantiated");
    }

    public static void save(UserId userId, Submission submission) {
        if (submission.getStatus() != SubmissionStatus.IN_PROGRESS) {
            throw new IllegalArgumentException("Only IN_PROGRESS submissions can be stored in buffer");
        }
        BUFFER.put(userId, submission);
    }

    public static Optional<Submission> findLastByUserId(UserId userId) {
        return Optional.ofNullable(BUFFER.get(userId));
    }

    public static void removeByUserId(UserId userId) {
        BUFFER.remove(userId);
    }

    public static void clear() {
        BUFFER.clear();
    }
}

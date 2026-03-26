# challenge-service

Microservice responsible for the challenge catalog, user submissions, activity and global stats.

For general architecture guidelines shared across all backend services see [`backend/README.md`](../README.md).

---

## Bounded contexts

| Module | Root Aggregate | Responsibility |
|---|---|---|
| `catalog` | `Challenge` | Challenge catalog management |
| `submission` | `Submission` | User solution lifecycle |
| `activity` | `Activity` | Favorites and bookmarks per user |
| `stat` | `ChallengeStats` | Global counters per challenge |

---

## Shared

The `shared` package contains value objects used across multiple bounded contexts within this service.

| Class | Description |
|:------|:------------|
| `UserId` | Opaque identifier for a user. Owned by `account-service`, referenced here as a value object. |

---

## Architecture Decision Records

| ADR | Decision |
|---|---|
| [ADR 001](docs/adr/001-catalog-bounded-context.md) | challenge-service internal organization into bounded contexts |

---

## Domain rules

### Submission lifecycle

A user can only have 1 submission per challenge. Once it reaches a terminal state it cannot be changed.

| From          | To             | Allowed                      |
|:--------------|:---------------|:-----------------------------|
| `NONE`        | `IN_PROGRESS`  | ✅ save draft                |
| `NONE`        | `FINAL`        | ✅ finalize directly         |
| `NONE`        | `INCOMPLETE`   | ✅ mark incomplete directly  |
| `IN_PROGRESS` | `FINAL`        | ✅                           |
| `IN_PROGRESS` | `INCOMPLETE`   | ✅                           |
| `FINAL`       | any            | ❌ terminal state            |
| `INCOMPLETE`  | any            | ❌ terminal state            |

Once a submission reaches `FINAL` or `INCOMPLETE`, the official solution of the challenge is revealed to the user.

### Activity rules

| Action          | Event emitted     | Counter updated                  |
|:----------------|:------------------|:---------------------------------|
| Mark favorite   | `FavoriteAdded`   | `ChallengeStats.favorites + 1`   |
| Unmark favorite | `FavoriteRemoved` | `ChallengeStats.favorites - 1`   |
| Mark bookmark   | `BookmarkAdded`   | `ChallengeStats.bookmarks + 1`   |
| Unmark bookmark | `BookmarkRemoved` | `ChallengeStats.bookmarks - 1`   |

### Stat rules

`ChallengeStats` maintains global counters per challenge across all users.

| Counter       | Description                                                  |
|:--------------|:-------------------------------------------------------------|
| `timesDone`   | Total number of users who finalized the challenge            |
| `favorites`   | Total number of users who marked the challenge as favorite   |
| `bookmarks`   | Total number of users who bookmarked the challenge           |

Counters are updated in the same transaction as the action that triggers them and never go below 0.

---

## Run locally
```bash
./gradlew :challenge-service:bootRun
```

## Tests
```bash
./gradlew :challenge-service:test
```
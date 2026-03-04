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
| `stats` | `ChallengeStats` | Global counters per challenge |

---

## Architecture Decision Records

| ADR | Decision |
|---|---|
| [ADR 001](docs/adr/001-catalog-bounded-context.md) | challenge-service internal organization into bounded contexts |

---

## Domain rules

### Submission lifecycle

A user can only have 1 submission per challenge. Once it reaches a terminal state it cannot be changed.

| From         | To          | Allowed                       |
|:-------------|:------------|:------------------------------|
| `NONE`       | `IN_PROGRESS` | ✅ save draft               |
| `NONE`       | `FINAL`     | ✅ finalize directly          |
| `NONE`       | `INCOMPLETE`| ✅ mark incomplete directly   |
| `IN_PROGRESS`| `FINAL`     | ✅                            |
| `IN_PROGRESS`| `INCOMPLETE`| ✅                            |
| `FINAL`      | any         | ❌ terminal state             |
| `INCOMPLETE` | any         | ❌ terminal state             |

### Activity rules

| Action | Event emitted | Counter updated |
|:-------|:-------------|:----------------|
| Mark favorite | `FavoriteAdded` | `ChallengeStats.favorites + 1` |
| Unmark favorite | `FavoriteRemoved` | `ChallengeStats.favorites - 1` |
| Mark bookmark | `BookmarkAdded` | `ChallengeStats.bookmarks + 1` |
| Unmark bookmark | `BookmarkRemoved` | `ChallengeStats.bookmarks - 1` |

---

## Run locally
```bash
./gradlew :challenge-service:bootRun
```

## Tests
```bash
./gradlew :challenge-service:test
```
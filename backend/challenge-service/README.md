# challenge-service

Microservice responsible for the challenge catalog, user submissions, activity and global stats.

For general architecture guidelines shared across all backend services see [`backend/README.md`](../README.md).

---

## Bounded contexts

| Module | Root Aggregate | Responsibility |
|---|---|---|
| `catalog` | `Challenge` | Challenge catalog management |
| `submissions` | `Submission` | User solution lifecycle |
| `activity` | `UserActivity` | Favorites and bookmarks per user |
| `stats` | `ChallengeStats` | Global counters per challenge |

---

## Architecture Decision Records

| ADR | Decision |
|---|---|
| [ADR 001](docs/adr/001-catalog-bounded-context.md) | challenge-service internal organization into bounded contexts |

---

## Run locally

```bash
./gradlew :challenge-service:bootRun
```

## Tests

```bash
./gradlew :challenge-service:test
```
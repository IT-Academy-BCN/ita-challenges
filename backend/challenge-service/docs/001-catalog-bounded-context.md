# ADR 001 — challenge-service internal organization into bounded contexts

## Status
Accepted

## Date
2026-02-26

## Context

At the start of the project the `CreateChallenge` code lived in a flat structure with no defined bounded contexts. After analyzing the functional map we identified that `challenge-service` has four clearly differentiated responsibilities that change for different reasons and are driven by different actors.

We needed to decide how to organize the code internally to make it maintainable, scalable and serve as a pedagogical reference for academy students.

## Key concepts

### What is an aggregate?

An aggregate is a cluster of domain objects that must change together to maintain business rules consistency. It has a single entry point called the **aggregate root**, and anything that wants to modify something inside the cluster must go through it.

The aggregate root is the guardian of **invariants** — rules that can never be broken. For example: a user can only have 1 submission per challenge in a terminal state.

An aggregate is a pure Java class with attributes and business methods. It has no knowledge of databases, frameworks or other aggregates. Persistence is handled by a Repository (infrastructure), which loads and saves the aggregate without the aggregate knowing about it.

**Rule of thumb:** if two things change for different reasons or are driven by different actors, they are likely different aggregates.

## Decision

We organize `challenge-service` into four internal modules, each with its own root aggregate and three layers (domain, application, infrastructure):

**`catalog`** — aggregate `Challenge`
Manages the challenge catalog: statement, language, tags, difficulty, official solution and resources. Only admins can create, edit and delete challenges.

**`submission`** — aggregate `Submission`
Manages the lifecycle of a user's solution for a challenge. A user can transition to any status directly without going through IN_PROGRESS first. Key invariant: a user can only have 1 submission per challenge, and once it reaches a terminal state (FINAL or INCOMPLETE) it cannot be changed.

**`activity`** — aggregate `Activity`
Manages user actions on a challenge: mark/unmark favorite and bookmark.

**`stat`** — aggregate `ChallengeStats`
Maintains global counters for a challenge: timesDone, favorites, bookmarks. Updated in the same transaction as submissions and activity.

We also introduce a `shared` package for value objects used across multiple bounded contexts, such as `UserId`, which is owned by `account-service` and referenced here as an opaque identifier.

## Consequences

**Positive**
- Each module has a single reason to change.
- Aggregates are small and cheap to load: we do not mix data that changes for different reasons.
- Official solution visibility (only if the user has FINAL or INCOMPLETE) is resolved in the `GetChallengeDetailUseCase`, which queries both aggregates and decides what to expose. The domain remains pure.
- Counters updated in the same transaction avoid inconsistencies without needing events or sagas.
- The structure is easily scalable: if a module grows, it can be extracted into its own microservice without touching the others.

**Negative / trade-offs**
- More classes and packages than a traditional layered architecture.
- Use cases that need data from multiple modules (like `GetChallengeDetailUseCase`) must coordinate multiple repositories.

## Alternatives considered

**Put submissions inside Challenge**
Discarded because a popular challenge could have thousands of submissions. Loading the entire aggregate to modify one submission is not viable and conceptually wrong: a challenge and a submission change driven by different actors.

**Put stats inside Challenge**
Discarded because counters are updated very frequently (every favorite, every submission) while the challenge itself is rarely edited. Merging them would force loading the full statement just to increment a counter.

**Move submissions to account-service**
Discarded because the business rules of submission depend on the challenge, not the user. If submissions lived in account-service, it would need to call challenge-service to validate, creating write-time coupling between services.
# Challenge Storage

## Current situation

Challenge data is currently stored only in memory, so it is lost when the
application restarts.

## MVP solution

For the MVP, the project uses a lightweight, read-only JSON seed. This keeps
initial challenge examples available between restarts without adding database
infrastructure yet, and simplifies local testing and demos.

## Options considered

- In-memory only: simplest option, but every restart removes all challenges.
- Read-only JSON seed: enough for MVP demos and local development.
- Writable JSON: avoided because runtime writes are fragile once packaged.
- Database: better for production, but premature for this MVP stage.

## Seed data

The seed file is stored in:

```text
backend/challenge-service/src/main/resources/challenges-seed.json
```

It lives in `src/main/resources` so Spring can load it from the classpath in all
environments. It must not store runtime changes, user activity, submissions, or
any mutable application state.

## Future improvements

As the project evolves, this may be replaced by a database solution better
suited for production and advanced features.

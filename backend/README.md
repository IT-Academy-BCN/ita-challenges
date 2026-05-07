git branch -d add-pedro-spook-contributors# Backend

This directory contains all backend microservices for the ITA Challenges platform.

All services are built with **Spring Boot** and follow the same architectural principles.

---

## Architecture

All microservices follow **Hexagonal Architecture (ports & adapters)** with **Domain-Driven Design (DDD)** principles.

### Package structure

Each microservice is organized by **bounded contexts**. Within each bounded context the structure is:

```
{bounded-context}/
├── domain/           # Pure Java — no frameworks, no dependencies
│   ├── port/
│   │   ├── in/       # Use case interfaces (driving ports)
│   │   └── out/      # Repository interfaces (driven ports)
│   ├── valueobject/
│   └── exception/
├── application/      # Orchestrates use cases, holds DTOs
│   ├── usecase/
│   └── dto/
└── infrastructure/   # Spring, JPA, REST — implements the ports
    └── adapter/
        ├── in/web/
        └── out/persistence/
```

### Dependency rules

- `domain` knows nobody. Pure Java, no frameworks.
- `application` knows `domain`. Orchestrates use cases.
- `infrastructure` knows `application` and `domain`. Implements the ports.

### Key concepts

**Aggregate** — a cluster of domain objects that must change together to maintain business rules consistency. It has a single entry point called the aggregate root, which is the guardian of invariants. An aggregate is a pure Java class with no knowledge of databases or frameworks.

**Use case** — a single operation the system can perform. One class, one method `execute()`. Lives in `application/usecase/`.

**Port** — an interface that decouples the domain from the outside world. Driving ports (`in/`) define what the application can do. Driven ports (`out/`) define what the application needs (e.g. a repository).

**Adapter** — the implementation of a port. Lives in `infrastructure/`.

---

## Microservices

| Service | Responsibility | Docs |
|---|---|---|
| `challenge-service` | Challenge catalog, submissions, activity and stats | [README](./challenge-service/README.md) |
| `account-service` | User identity, profile, ranking and CodeConnect | [README](./account-service/README.md) |
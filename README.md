# ita-challenges

Monorepo for the ItaChallenges platform. This repository centralizes the backend and frontend codebases, shared modules, and project documentation to support the development of the IT Academy's challenge-based learning system.

---

# Local development

## Prerequisites
- Docker + Docker Compose
- Node 20+ (only required if running the frontend with `ng serve`)

---

# Quick start (recommended)

Run the **full stack (gateway + frontend + backend)** through a single entrypoint:

```bash
docker compose --profile full up --build
```

Open:
- http://localhost:8080

This simulates the production/cloud architecture (API Gateway → services).

---

# Development modes

## 🟢 Mode 1 — Frontend fast iteration (recommended for daily dev)

Best for working on Angular (components, UI, styles, routing, etc.).  
Uses hot reload (HMR) and is much faster than rebuilding Docker images.

### Start backend only (Docker)
```bash
docker compose --profile backend \
  -f docker-compose.yml \
  -f docker-compose.backend.override.yml up --build
```

### Start frontend locally (no Docker)
```bash
cd frontend
npm install
npx ng serve --proxy-config proxy.conf.json
```

Open:
- http://localhost:4200

The Angular dev server uses a proxy so API calls still go through the gateway automatically.

---

## 🔵 Mode 2 — Full stack (production-like validation)

Best for:
- integration testing
- demos
- validating Docker images
- checking gateway routing
- “how it will run in AWS”

```bash
docker compose --profile full up --build
```

Open:
- http://localhost:8080

Note: frontend changes require rebuilding the image (slower).

---

# Gateway routing

The local nginx gateway routes:

- `/` → frontend
- `/itachallenge/api/v1/users/**` → user-service
- `/itachallenge/api/v1/challenges/**` → challenge-service
- `/actuator/users/**` → user-service actuator
- `/actuator/challenges/**` → challenge-service actuator

---

# Health checks

You can verify services are up:

- http://localhost:8080/actuator/users/health
- http://localhost:8080/actuator/challenges/health

Docker uses these endpoints for container healthchecks and startup ordering.

---

# Architecture notes

- All backend services run internally on port **8080**
- Only the **gateway** exposes ports to the host
- Compose profiles:
  - `full` → gateway + frontend + backend
  - `backend` → backend only
  - `frontend` → frontend only
- Angular must call **relative paths only** (no hardcoded localhost URLs)

✅ Good:
```
/itachallenge/api/v1/users
```

❌ Avoid:
```
http://localhost:8081/itachallenge/api/v1/users
```

---

# Tips

Rebuild only frontend:
```bash
docker compose --profile full up -d --build frontend
```

Stop everything:
```bash
docker compose down
```

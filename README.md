# ita-challenges

Monorepo for the ItaChallenges platform.

This repository centralizes:
- Backend microservices (Spring Boot)
- Frontend (Angular)
- Local gateway (Nginx)
- Infrastructure and documentation

It allows running the whole platform locally in a production-like architecture similar to cloud/AWS.

---

# 🧰 Local development

## Prerequisites

- Docker Desktop (Docker + Docker Compose)
- Node.js 22 (LTS) + npm (only required if running the frontend locally with `ng serve`)

---

# 🚀 Quick start (recommended)

Run the full stack (gateway + frontend + backend):

```bash
docker compose --profile full up --build
```

Open:

http://localhost:8080

This simulates the real production architecture:

Gateway → Frontend → Backend services

---

# 🧩 Development modes

## 🟢 Mode 1 — Frontend fast iteration (recommended daily)

Best for:
- UI work
- components
- styles
- routing
- fast feedback (HMR / hot reload)

### Start backend only (Docker)

```bash
docker compose \
  --profile backend \
  -f docker-compose.yml \
  -f docker-compose.backend.override.yml \
  up --build
```

Backend access from the host depends on whether you use the override file:

- With `docker-compose.backend.override.yml` → ports are exposed:
  - challenge-service → http://localhost:8081
  - account-service → http://localhost:8082
- Without the override → ports are NOT exposed (containers are internal-only).

---

### Start frontend locally (no Docker)

```bash
cd frontend
npm install
npm run start:local
```

Open:

http://localhost:4200

Angular uses a proxy to route API calls.

- If the gateway is running: API calls go through `http://localhost:8080` (recommended).
- If only backend is running (`--profile backend`): adjust the proxy target to the exposed ports (see `frontend/README.md`).

---

## 🔵 Mode 2 — Full stack (production-like validation)

Best for:
- integration testing
- demos
- validating Docker images
- testing gateway routing
- simulating AWS behavior

```bash
docker compose --profile full up --build
```

Open:

http://localhost:8080

Note: frontend changes require rebuilding the Docker image (slower than HMR).

---

# 🌐 Gateway routing

In `full` mode, APIs are accessed through the gateway with the `/itachallenge` prefix.

Internal services expose their APIs without that prefix.

Routing table (gateway only):

| Path (via gateway) | Service |
|--------------------|----------|
| / | frontend |
| /itachallenge/api/v1/challenges | challenge-service |
| /actuator/challenges/** | challenge-service actuator |
| /actuator/users/** | account-service actuator |
| /itachallenge/api/v1/users | account-service (planned / TODO) |

---

# ❤️ Health checks & smoke tests

All backend services expose:

GET /actuator/health (inside the container on port 8080)

Docker Compose uses these endpoints to:
- verify containers are ready
- control startup order
- avoid the gateway starting too early

---

## ✅ FULL stack (gateway enabled)

Health (via gateway):

```bash
curl -fsS http://localhost:8080/actuator/challenges/health
curl -fsS http://localhost:8080/actuator/users/health
```

Expected:

```json
{"status":"UP"}
```

Smoke test (API via gateway):

```bash
curl -i -X POST http://localhost:8080/itachallenge/api/v1/challenges \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","description":"Created through gateway"}'
```

Expected:
- 201 Created
- JSON containing an `id`

---

## ✅ BACKEND only (no gateway)

### Option A — With override (ports exposed)

```bash
docker compose \
  --profile backend \
  -f docker-compose.yml \
  -f docker-compose.backend.override.yml \
  up --build
```

Health from host:

```bash
curl -fsS http://localhost:8081/actuator/health
curl -fsS http://localhost:8082/actuator/health
```

API test directly:

```bash
curl -i -X POST http://localhost:8081/api/v1/challenges \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","description":"Created through exposed port 8081"}'
```

### Option B — Without override (ports NOT exposed)

Containers are internal-only.

Use docker exec:

```bash
docker compose exec account-service curl -fsS http://localhost:8080/actuator/health
docker compose exec challenge-service curl -fsS http://localhost:8080/actuator/health
```

---

# 🏗 Architecture notes

- All backend services listen on port 8080 inside Docker
- Gateway adds the `/itachallenge` prefix in full mode
- Host port exposure depends on the mode:
  - full mode: gateway exposes 8080
  - backend mode: ports are exposed only if using docker-compose.backend.override.yml
- Backend images are built using the Gradle Wrapper
- Frontend is served by Nginx inside Docker
- Local setup mirrors how services will run in cloud/AWS

---

# 🧪 Docker Compose profiles

| Profile | Starts |
|-----------|-----------------------------|
| full | gateway + frontend + backend |
| backend | backend only |
| frontend | frontend only |

Examples:

```bash
docker compose --profile full up
docker compose --profile backend up
docker compose --profile frontend up
```

---

# 🛑 Stopping containers (important when using profiles)

When using Docker Compose profiles, always use the same profile with `down` as you used with `up`.

Stop full stack:

```bash
docker compose --profile full down
```

Stop backend-only:

```bash
docker compose --profile backend down
```

Stop frontend-only:

```bash
docker compose --profile frontend down
```

Full reset (containers + volumes):

```bash
docker compose --profile full down -v
```

---

# 🅰️ Angular best practices

Always use relative URLs (gateway paths), so the same frontend build works locally and in cloud.

Good:

```
/itachallenge/api/v1/challenges
```

Avoid:

```
http://localhost:8081/api/v1/challenges
```

Note: `/itachallenge/api/v1/users` will be available once account-service implements its first endpoints.

---

# 🔀 Testing a backend feature branch (frontend workflow)

Frontend developers can test backend changes without waiting for merge to `develop`.

```bash
git fetch
git checkout <backend-branch>

docker compose \
  --profile backend \
  -f docker-compose.yml \
  -f docker-compose.backend.override.yml \
  up --build
```

---

# 🛠 Useful commands

Rebuild only frontend:

```bash
docker compose --profile full up -d --build frontend
```

View logs:

```bash
docker compose logs -f
```
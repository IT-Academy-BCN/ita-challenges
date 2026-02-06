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
- Node 24+ (only required if running the frontend locally with `ng serve`)

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

This mode starts backend services. If you need the local gateway for routing (recommended), run the full stack instead.

```bash
docker compose \
  --profile backend \
  -f docker-compose.yml \
  -f docker-compose.backend.override.yml \
  up --build
```

Backend ports exposed to the host (via override):
- challenge-service → http://localhost:8081
- user-service → http://localhost:8082

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
- If only backend is running (`--profile backend`): you must adjust the proxy target to the exposed ports (see `frontend/README.md`).

For more frontend details (proxy modes, scripts), see `frontend/README.md`.

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

Local Nginx routes requests as follows:

| Path | Service |
|------|---------|
| / | frontend |
| /itachallenge/api/v1/users/** | user-service |
| /itachallenge/api/v1/challenges/** | challenge-service |
| /actuator/users/** | user-service actuator |
| /actuator/challenges/** | challenge-service actuator |

---

# ❤️ Health checks

All backend services expose:

/actuator/health

Docker uses these endpoints to:
- verify containers are ready
- control startup order
- avoid the gateway starting too early

Manual checks (when gateway is running):

- http://localhost:8080/actuator/users/health
- http://localhost:8080/actuator/challenges/health

---

# 🏗 Architecture notes

- All backend services run internally on port 8080
- Only the gateway exposes ports to the host in `full` mode
- Backend images are built using the Gradle Wrapper (reproducible builds)
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

# 🅰️ Angular best practices

Always use relative URLs.

✅ Good:

/itachallenge/api/v1/users

❌ Avoid:

http://localhost:8081/itachallenge/api/v1/users

The gateway handles routing automatically.

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

This runs the backend using the code from the selected branch.

---

# 🛠 Useful commands

### Rebuild only frontend

```bash
docker compose --profile full up -d --build frontend
```

### Stop everything

```bash
docker compose down
```

### Full reset (containers + volumes)

```bash
docker compose down -v
```

### View logs

```bash
docker compose logs -f
```

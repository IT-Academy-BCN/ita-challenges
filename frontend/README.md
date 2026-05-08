# Frontend (Angular)

Angular application for the ItaChallenges platform.

---

# Quick start (recommended)

## Prerequisites

- Node.js 22 (LTS)
- npm (comes with Node)
- Docker Desktop

---

# 1) Start backend

From the repository root:

## Option A — Backend only

```bash
docker compose \
  --profile backend \
  -f docker-compose.yml \
  -f docker-compose.backend.override.yml \
  up --build
```

Services exposed:

- challenge-service → http://localhost:8081  
- account-service → http://localhost:8082  

---

## Option B — Full stack (recommended, production-like)

Starts gateway + backend.

```bash
docker compose --profile full up --build
```

Gateway:

http://localhost:8080

All frontend API calls go through the gateway.

---

# 2) Start frontend

From `frontend/`:

```bash
npm ci
npm run start:local
```

Open:

http://localhost:4200

---

# API and proxy configuration

The frontend NEVER calls backend services directly.

All calls go through the gateway using this base path:

```
/itachallenge/api
```

Example frontend call:

```
GET /itachallenge/api/v1/challenges
```

Angular proxy redirects this automatically.

---

# Proxy modes

The project uses proxy files:

```
proxy.local.json
proxy.dev.example.json
```

---

## Local mode (default)

File:

```
proxy.local.json
```

Content:

```json
{
  "/itachallenge/api": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true,
    "logLevel": "debug"
  },
  "/actuator": {
    "target": "http://localhost:8080",
    "secure": false,
    "changeOrigin": true,
    "logLevel": "debug"
  }
}
```

Run:

```bash
npm run start:local
```

---

## Dev / AWS mode

Create local file from example:

macOS / Linux:

```bash
cp proxy.dev.example.json proxy.dev.json
```

Windows:

```bash
copy proxy.dev.example.json proxy.dev.json
```

Example content:

```json
{
  "/itachallenge/api": {
    "target": "https://REPLACE_WITH_DEV_GATEWAY_DOMAIN",
    "secure": true,
    "changeOrigin": true,
    "logLevel": "debug"
  },
  "/actuator": {
    "target": "https://REPLACE_WITH_DEV_GATEWAY_DOMAIN",
    "secure": true,
    "changeOrigin": true,
    "logLevel": "debug"
  }
}
```

Run:

```bash
npm run start:dev
```

Notes:

- proxy.dev.json is NOT committed
- Allows switching environments without changing code

---

# Useful scripts

```bash
npm run start:local
npm run start:dev
npm run build
npm run test
```

---

# Project structure

```
src/app/
  core/
  shared/
  features/
  layout/
```

---

# Features

```
auth
challenges
solutions
profile
admin
```

Each feature contains:

```
pages/
components/
data-access/
models/
```

---

# Architecture rules

Must follow strictly:

- Only data-access/ may use HttpClient
- Pages must NOT call HttpClient directly
- Features must NOT import other features
- shared contains reusable UI only
- core contains global services only
- layout contains application shell only

---

# Routing

Routing is feature-based and lazy-loaded.

Frontend routes:

```
/auth
/challenges
/solutions
/profile
/admin
```

Backend prefix:

```
/itachallenge/api
```

This prefix is handled by gateway and proxy.

Frontend routes MUST NOT include backend prefixes.

Correct:

```
/challenges
```

Incorrect:

```
/itachallenge/api/challenges
```

---

# Development workflow

Recommended workflow:

1. Start backend

```bash
docker compose --profile full up --build
```

2. Start frontend

```bash
npm run start:local
```

3. Develop features

4. Commit changes

---

# Production readiness

This setup is compatible with:

- Local Docker development
- Staging environments
- AWS deployment
- Gateway-based routing
- Microservices architecture

No frontend code changes required between environments.

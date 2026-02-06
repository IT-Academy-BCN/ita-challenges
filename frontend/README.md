# Frontend (Angular)

Angular application for the ItaChallenges platform.

---

# ✅ Quick start (recommended)

## Local development (fast iteration)

This is the recommended mode for daily frontend work (HMR / hot reload).

## Prerequisites

- Node.js 22 (LTS)
- npm (comes with Node)


### 1) Start backend (Docker)

From the repository root:

```bash
docker compose \
  --profile backend \
  -f docker-compose.yml \
  -f docker-compose.backend.override.yml \
  up --build
```

Backend ports exposed to the host:
- challenge-service → http://localhost:8081
- user-service → http://localhost:8082

> Note: If you want gateway-based routing (recommended, production-like), run:
> `docker compose --profile full up --build`
> and the gateway will be available at `http://localhost:8080`.

### 2) Start frontend (Angular dev server)

```bash
npm install
npm run start:local
```

Open:

http://localhost:4200

---

# 🌐 Proxy modes (local vs dev)

The frontend uses Angular proxy configuration so API calls can be made with relative URLs (no hardcoded hosts).

## Local (default)

By default, `proxy.local.json` targets the local gateway:

- http://localhost:8080

Command:

```bash
npm run start:local
```

Proxy file:

- `proxy.local.json`

### If you are running backend only (no gateway)

When you run only `--profile backend`, there is no gateway at `http://localhost:8080`.
In that case you can either:
- run `--profile full` to use the gateway (recommended), or
- temporarily change the proxy target(s) to the exposed backend ports (8081/8082)

---

## Dev / AWS (future)

This mode will be used once a dev domain exists (AWS / cloud).

1) Copy the example file:

```bash
# macOS / Linux
cp proxy.dev.example.json proxy.dev.json

# Windows (PowerShell)
copy proxy.dev.example.json proxy.dev.json
```

2) Replace the placeholder domain in `proxy.dev.json`

3) Run:

```bash
npm run start:dev
```

Notes:
- `proxy.dev.json` is intentionally not committed (each developer can use their own environment).
- `proxy.dev.example.json` is committed as a template.

---

# 🧪 Useful scripts

```bash
npm start             # alias for start:local
npm run start:local   # ng serve with local proxy
npm run start:dev     # ng serve with dev proxy (requires proxy.dev.json)
npm run build         # production build
npm run test          # unit tests
```

---

# 🧱 Angular CLI reference

This project was generated using Angular CLI.

- Docs: https://angular.dev/tools/cli

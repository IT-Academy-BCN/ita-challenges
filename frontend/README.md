# Frontend (Angular)

Angular application for the ItaChallenges platform.

---

# ✅ Quick start (recommended)

## Local development (fast iteration)

This is the recommended mode for daily frontend work (HMR / hot reload).

### 1) Start backend + gateway (Docker)

From the repository root:

```bash
docker compose \
  --profile backend \
  -f docker-compose.yml \
  -f docker-compose.backend.override.yml \
  up --build
```

> Note: This mode starts backend containers. API calls are expected to go through the local gateway at `http://localhost:8080`.

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

Uses the local gateway (Docker) at:

- http://localhost:8080

Command:

```bash
npm run start:local
```

Proxy file:

- `proxy.local.json`

## Dev / AWS (future)

This mode will be used once a dev domain exists (AWS / cloud).

1) Copy the example file:

```bash
cp proxy.dev.example.json proxy.dev.json
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
npm run start:local   # ng serve with local proxy
npm run start:dev     # ng serve with dev proxy (requires proxy.dev.json)
npm run build         # production build
npm run test          # unit tests
```

---

# 🧱 Angular CLI reference

This project was generated using Angular CLI.

- Docs: https://angular.dev/tools/cli

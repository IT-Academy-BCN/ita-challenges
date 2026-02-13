# Frontend (Angular)

Angular application for the ItaChallenges platform.

---

# ✅ Quick start (recommended)

## Local development (fast iteration)

This is the recommended mode for daily frontend work (HMR / hot reload).

## Prerequisites

- Node.js 22 (LTS)
- npm (comes with Node)

---

## 1) Start backend (Docker)

From the repository root:

docker compose \
--profile backend \
-f docker-compose.yml \
-f docker-compose.backend.override.yml \
up --build

Backend ports exposed to the host:

- challenge-service → http://localhost:8081
- user-service → http://localhost:8082

Note: If you want gateway-based routing (recommended, production-like), run:

docker compose --profile full up --build

Gateway:

http://localhost:8080

---

## 2) Start frontend (Angular dev server)

npm ci  
npm run start:local

Open:

http://localhost:4200

---

# 🌐 Proxy modes (local vs dev)

The frontend uses Angular proxy configuration so API calls can be made with relative URLs.

---

## Local (default)

Uses:

proxy.local.json

Targets:

http://localhost:8080

Run:

npm run start:local

---

## Dev / AWS (future)

Copy template:

macOS / Linux:

cp proxy.dev.example.json proxy.dev.json

Windows:

copy proxy.dev.example.json proxy.dev.json

Edit domain.

Run:

npm run start:dev

proxy.dev.json is not committed.

---

# 🧪 Useful scripts

npm start  
npm run start:local  
npm run start:dev  
npm run build  
npm run test

---

# 🏗 Project structure

src/app/

core/  
shared/  
features/  
layout/

---

## Features

auth  
challenges  
solutions  
profile  
admin

---

## Architecture rules

Only data-access/ should use HttpClient.

Features must not import other features directly.

Shared contains reusable UI only.

Core contains global services.

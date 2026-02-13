# Frontend guidelines

## Goal

Keep the code easy to understand for bootcamp students while remaining scalable and production-ready.

---

# Folder rules

## core/

Global infrastructure only.

Contains singleton services.

Examples:

- auth services
- guards
- interceptors

Must NOT contain business logic.

---

## shared/

Reusable UI and utilities.

Contains:

- components
- pipes
- directives
- utils
- models

Must NOT contain API calls.

---

## features/

Business functionality grouped by domain.

Each feature:

feature-name/

pages/  
components/  
data-access/  
models/

Only data-access may call HttpClient.

---

## layout/

Application shell only.

Examples:

- header
- sidebar
- footer
- router shell

No business logic allowed.

---

# Import rules

Allowed:

feature → shared  
feature → core

Not allowed:

feature → feature

---

# HTTP rule

Only data-access may use HttpClient.

pages and components must use services.

---

# Naming conventions

pages:

*-page.component.ts

components:

*-component.ts

services:

*.service.ts

models:

*.model.ts

---

# Adding a feature

Create:

src/app/features/<feature-name>/

Add:

pages/  
components/  
data-access/  
models/

Add routes if needed.

---

# Architecture goals

Bootcamp-friendly  
Production-ready  
Scalable  
Maintainable  
Cloud-ready

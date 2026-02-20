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
- pages

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

routes:

    <feature-name>.routes.ts

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

---

# Routing rules

Routing is feature-based and uses lazy loading.

Defined in:

    src/app/app.routes.ts

Each feature defines its own routes in:

    src/app/features/<feature-name>/<feature-name>.routes.ts

This keeps the application scalable and prevents loading unused features.

Lazy loading ensures that feature code is only downloaded when the user navigates to it.

---

## URL design

Routes must use clean, domain-independent paths.

Correct:

    /auth
    /challenges
    /challenges/:id
    /profile

Incorrect:

    /itachallenges/auth
    /itachallenges/challenges

The frontend must NOT include deployment-specific prefixes.

The application domain, base path, and hosting configuration are handled outside Angular.

Never hardcode environment-specific prefixes in routes.

---

## Route ownership

### app.routes.ts

Responsible for:

- defining top-level routes
- loading features using lazy loading
- defining the layout shell
- defining the global 404 fallback

### Feature routes

Responsible for:

- defining routes inside the feature
- mapping URLs to pages

Example:

    /challenges      → ChallengesListPage
    /challenges/:id  → ChallengeDetailPage

---

## Page responsibility

Pages represent full screens.

Pages are the entry point for a route.

Pages may use:

- components
- data-access services

Pages must NOT call HttpClient directly.

Example:

    features/challenges/pages/challenges-list-page.component.ts
    features/challenges/pages/challenge-detail-page.component.ts

---

## Rule: 1 route = 1 page

Each important URL must have its own page.

Correct:

    /challenges      → ChallengesListPage
    /challenges/:id  → ChallengeDetailPage

Incorrect:

- One page handling multiple unrelated routes

This keeps routing simple and maintainable.

---

## 404 handling

The application defines a global fallback route:

    path: '**'

This renders:

    NotFoundPage

Location:

    src/app/shared/pages/not-found-page.component.ts

This prevents crashes and improves user experience.

---

## Adding routes to a feature

Step 1 — create pages

    features/<feature-name>/pages/

Example:

    challenges-list-page.component.ts
    challenge-detail-page.component.ts

Step 2 — register routes

    features/<feature-name>/<feature-name>.routes.ts

Example:

    export const CHALLENGES_ROUTES: Routes = [
      { path: '', component: ChallengesListPage },
      { path: ':id', component: ChallengeDetailPage },
    ];

Step 3 — lazy loading is already configured in app.routes.ts

No modification required unless adding a new feature.

---

## Layout and router shell

The layout contains the router outlet.

Location:

    src/app/layout/

The layout is responsible for:

- application shell
- header
- sidebar
- rendering pages via router-outlet

The layout must NOT contain business logic.

---

## Routing architecture goals

Feature-isolated  
Lazy-loaded  
Scalable  
Bootcamp-friendly  
Production-ready

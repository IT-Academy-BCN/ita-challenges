# GitHub Authentication Local Setup

## Goal

Provide clear and complete documentation to configure, run, and manually test the GitHub authentication flow in a local environment.

## Acceptance Criteria

- All required environment variables are specified
- The GitHub OAuth callback configuration is clearly defined
- The local URLs involved in the authentication flow are identified
- Step-by-step instructions to execute the login flow locally are provided
- The authentication flow can be successfully completed by following the documentation

## Minimal Validation

- A teammate follows the documented steps and successfully authenticates with GitHub, confirming the flow works and that `/profile` is the expected frontend destination once that integration is completed

---

## Required Environment Variables

Each developer must provide their own GitHub OAuth App credentials locally.

Required variables:

```bash
GITHUB_CLIENT_ID=your_client_id
GITHUB_CLIENT_SECRET=your_client_secret
```

These variables must be available to the backend when the local environment is started.

---

## GitHub OAuth App Configuration

Each developer should create their own OAuth App in GitHub for local testing.

### Steps

1. Open GitHub
2. Go to:
   - `Settings`
   - `Developer settings`
   - `OAuth Apps`
   - `New OAuth App`

### Local values

**Homepage URL**

```text
http://localhost:8080
```

**Authorization callback URL**

```text
http://localhost:8080/api/account/auth/github/callback
```

After creating the app, copy:

- Client ID
- Client Secret

And use them as the local environment variables listed above.

---

## Local URLs Involved in the Flow

These are the important local URLs in the authentication process.

### Application entry

```text
http://localhost:8080
```

### Login page

```text
http://localhost:8080/auth
```

### GitHub OAuth callback

```text
http://localhost:8080/api/account/auth/github/callback
```

### Expected frontend destination

```text
http://localhost:8080/profile
```

### Public account route prefix

Account-related public routes are expected to live under:

```text
/api/account/*
```

---

## How to Run the Project Locally

From the project root:

```bash
docker compose --profile full up --build
```

Then open:

```text
http://localhost:8080
```

This starts the local stack needed for the auth flow.

---

## How to Test the Login Flow Locally

### Step-by-step

1. Make sure your `GITHUB_CLIENT_ID` and `GITHUB_CLIENT_SECRET` are correctly configured
2. Start the local environment with Docker Compose
3. Open the application in the browser:

```text
http://localhost:8080
```

4. Navigate to the login page:

```text
http://localhost:8080/auth
```

5. Click the GitHub login button
6. Authenticate with GitHub
7. GitHub redirects back to the local application through the callback URL
8. After successful authentication, confirm that the backend flow completes correctly and that the expected frontend destination is:

```text
/profile
```

9. Once frontend integration is completed, verify that user data is displayed there, at minimum:
   - GitHub username
   - avatar if available

---

## Manual Validation Checklist

- GitHub OAuth App was created with the correct local callback URL
- Local environment variables are present
- The application starts locally without auth configuration errors
- The GitHub login button starts the authentication flow
- GitHub redirects back to the local app
- The backend auth flow completes correctly
- `/profile` is documented as the expected frontend destination

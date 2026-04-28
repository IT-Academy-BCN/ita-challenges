# GitHub Login — Callback Setup

## What this does
Defines where GitHub should send the user after they log in.

## What was done
- Set up the GitHub app with the correct return URL
- Confirmed our system already handles this return path with no extra changes needed
   find it at this path: /Users/macbookpro/IdeaProjects/ita-challenges/infra/gateway/nginx.conf
- (link : https://github.com/IT-Academy-BCN/ita-challenges/pull/398)

## Configuration used

| Field | Value |
|---|---|
| App URL | `http://localhost:8080` |
| Return URL | `http://localhost:8080/api/account/auth/github/callback` |

## What success looks like
- [ ] Clicking login sends the user to GitHub
- [ ] After login, the user is sent back to our app

## Notes
Screenshots of the GitHub setup are attached for reference.
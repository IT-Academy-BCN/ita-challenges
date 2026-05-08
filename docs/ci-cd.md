# CI/CD structure (monorepo)

This repository separates CI (validation) and CD (deployment) workflows for frontend and backend.

## CI workflows

- frontend-ci.yml
- backend-ci.yml

Purpose:
- Validate buildability
- Install dependencies
- Compile frontend/backend
- Ensure Dockerfiles can be built

Triggers:
- push to develop, staging, main
- pull_request to develop, staging, main
- only when relevant folders change (frontend/** or backend/**)

## CD workflows (deployment skeleton)

- frontend-deploy.yml
- backend-deploy.yml

Purpose:
- Provide a deployment entry point
- Currently acts as placeholder until AWS infrastructure is configured

Deploy logic is implemented in:

- infra/deploy/frontend.sh
- infra/deploy/backend.sh

These scripts:

- Accept environment name and commit SHA
- Perform deployment via docker compose when infra exists
- Exit successfully (no-op) if deployment secrets are not configured

## Environment mapping

Branch → Environment:

- develop → dev
- staging → staging
- main → production

## AWS / GitLab migration notes

GitHub Actions → GitLab CI mapping is straightforward:

- GitHub paths → GitLab rules:changes
- GitHub workflows → GitLab jobs
- deploy scripts remain reusable

AWS provider can replace the contents of:

infra/deploy/*.sh

with ECS / EKS / Terraform / Ansible logic while preserving the same interface.

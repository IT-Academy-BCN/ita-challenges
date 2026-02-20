#!/usr/bin/env bash
set -euo pipefail

ENV_NAME="${1:-develop}"
SHA="${2:-unknown}"

echo "[backend] Deploy requested env=${ENV_NAME} sha=${SHA}"

# Placeholder: if no infra/secrets, do nothing successfully.
if [[ -z "${SSH_HOST:-}" ]]; then
  echo "[backend] No SSH secrets configured. Placeholder deploy (OK)."
  echo "[backend] AWS team: replace this script with ECS/EKS/Terraform/Ansible as needed."
  exit 0
fi

: "${SSH_USER:?Missing SSH_USER}"
: "${SSH_PRIVATE_KEY:?Missing SSH_PRIVATE_KEY}"
: "${DEPLOY_PATH:?Missing DEPLOY_PATH}"
: "${SSH_PORT:=22}"

KEY_FILE="$(mktemp)"
chmod 600 "$KEY_FILE"
printf "%s" "$SSH_PRIVATE_KEY" > "$KEY_FILE"

ssh -i "$KEY_FILE" -p "$SSH_PORT" -o StrictHostKeyChecking=no "${SSH_USER}@${SSH_HOST}" "
  set -e
  cd '${DEPLOY_PATH}'
  git fetch --all
  git checkout '${ENV_NAME}'
  git pull --ff-only origin '${ENV_NAME}'

  COMPOSE_FILES='-f docker-compose.yml'
  if [ -f docker-compose.backend.override.yml ]; then
    COMPOSE_FILES=\"\$COMPOSE_FILES -f docker-compose.backend.override.yml\"
  fi

  docker compose \$COMPOSE_FILES --profile full up -d --build challenge-service account-service
  docker image prune -f
"

rm -f "$KEY_FILE"
echo "[backend] Done."

#!/usr/bin/env bash
set -euo pipefail

ENV_NAME="${1:-develop}"
SHA="${2:-unknown}"

echo "[frontend] Deploy requested env=${ENV_NAME} sha=${SHA}"

# Placeholder: if no infra/secrets, do nothing successfully.
if [[ -z "${SSH_HOST:-}" ]]; then
  echo "[frontend] No SSH secrets configured. Placeholder deploy (OK)."
  echo "[frontend] AWS team: replace this script with ECS/EKS/Terraform/Ansible as needed."
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
  docker compose --profile full up -d --build frontend
  docker image prune -f
"

rm -f "$KEY_FILE"
echo "[frontend] Done."

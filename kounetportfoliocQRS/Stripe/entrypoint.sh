#!/bin/sh

VAULT_ADDR=${VAULT_ADDR:-http://vault:8200}
VAULT_TOKEN=${VAULT_TOKEN:-root}

# Récupère la clé Stripe API
STRIPE_API_KEY=$(curl -s \
  --header "X-Vault-Token: $VAULT_TOKEN" \
  "$VAULT_ADDR/v1/secret/data/secret" | jq -r '.data.data["stripe.api.key"]')

# Récupère la clé secrète Stripe
STRIPE_SECRET_KEY=$(curl -s \
  --header "X-Vault-Token: $VAULT_TOKEN" \
  "$VAULT_ADDR/v1/secret/data/secret" | jq -r '.data.data["stripe.api.secret.key"]')

# Récupère le secret webhook Stripe
STRIPE_WEBHOOK_SECRET=$(curl -s \
  --header "X-Vault-Token: $VAULT_TOKEN" \
  "$VAULT_ADDR/v1/secret/data/secret" | jq -r '.data.data["stripe.webhook.secret"]')

if [ -z "$STRIPE_API_KEY" ] || [ "$STRIPE_API_KEY" = "null" ]; then
  echo "La clé Stripe API n'a pas pu être récupérée depuis Vault."
  exit 1
fi

if [ -z "$STRIPE_SECRET_KEY" ] || [ "$STRIPE_SECRET_KEY" = "null" ]; then
  echo "La clé Stripe SECRET n'a pas pu être récupérée depuis Vault."
  exit 1
fi

if [ -z "$STRIPE_WEBHOOK_SECRET" ] || [ "$STRIPE_WEBHOOK_SECRET" = "null" ]; then
  echo "Le secret webhook Stripe n'a pas pu être récupéré depuis Vault."
  exit 1
fi

export STRIPE_API_KEY STRIPE_SECRET_KEY STRIPE_WEBHOOK_SECRET

stripe listen --forward-to host.docker.internal:8083/stripe/webhook

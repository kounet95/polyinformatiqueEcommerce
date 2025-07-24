#!/bin/sh

# Attente que Vault soit prêt
echo "Attente que Vault soit prêt..."
until curl -s http://vault:8200/v1/sys/health > /dev/null; do
  sleep 2
done

echo "Vault est prêt. Injection des secrets..."

# Configuration du token root
export VAULT_ADDR='http://vault:8200'
export VAULT_TOKEN='root'

# Ajout des secrets
vault kv put secret/postgres username=admin password=1234
vault kv put secret/keycloak admin_user=admin admin_password=admin
vault kv put secret/stripe.webhook.secret stripe.webhook.secret=whsec_0670b795359d49a33c66493d20e1a0d9719bd96c4a5cb1733066817adc776d7d
vault kv put secret/stripe.api.key stripe.api.key=sk_test_51RjaG74EMj4mRh4IzbxbXs4MULKMgGSb0ewvZDAFktUIh56FnxNn8P0Q5caIZhrPbZIz729BCfxTnjI6hcG6ePqG00YhqbsMJD
vault kv put secret/stripe.api.secret.key stripe.api.secret.key=sk_test_51RjaG74EMj4mRh4IzbxbXs4MULKMgGSb0ewvZDAFktUIh56FnxNn8P0Q5caIZhrPbZIz729BCfxTnjI6hcG6ePqG00YhqbsMJD


echo "Secrets injectés avec succès."

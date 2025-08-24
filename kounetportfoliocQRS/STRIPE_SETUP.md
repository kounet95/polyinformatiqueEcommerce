# Stripe Configuration Setup

## Issue Resolution: Stripe CLI Authentication Timeout

This document explains how to resolve the Stripe CLI authentication timeout issue and properly configure Stripe for the ecommerce application.

## Problem Description

The original issue was:
```
Checking for new versions...
You have not configured API keys yet. Running `stripe login`...
Your pairing code is: safe-superb-envy-pros
This pairing code verifies your authentication with Stripe.
To authenticate with Stripe, please go to: https://dashboard.stripe.com/stripecli/confirm_auth?t=...
Waiting for confirmation...
exceeded max attempts
```

## Root Cause

The Stripe CLI service in Docker Compose was trying to authenticate without proper API keys configured, leading to authentication timeouts.

## Solution

### 1. Environment Variables Setup

Create or update the `.env` file in the project root with your actual Stripe keys:

```bash
# Stripe Configuration
STRIPE_API_KEY=sk_test_your_actual_stripe_secret_key
STRIPE_SECRET_KEY=sk_test_your_actual_stripe_secret_key
STRIPE_WEBHOOK_SECRET=whsec_your_actual_webhook_secret
```

### 2. Get Your Stripe Keys

1. Go to [Stripe Dashboard](https://dashboard.stripe.com/apikeys)
2. Copy your **Secret key** (starts with `sk_test_` for test mode)
3. For webhook secret:
   - Go to [Webhooks](https://dashboard.stripe.com/webhooks)
   - Create a new webhook endpoint pointing to your application
   - Copy the **Signing secret** (starts with `whsec_`)

### 3. Configuration Files Updated

The following files have been updated to support proper Stripe configuration:

#### `.env` file
- Contains environment variables for Stripe keys
- Used by Docker Compose services

#### `config-rif/ecPolyCommand.properties`
- Configuration for the ecPolyCommand service
- Maps environment variables to Spring Boot properties
- Includes database and logging configuration

#### `compose.yaml`
- Updated ecPolyCommand service to include Stripe environment variables
- Modified Stripe CLI service to handle missing keys gracefully
- Added proper error handling and conditional startup

#### `StripeServiceImpl.java`
- Added validation and warning messages for missing API keys
- Improved logging for debugging configuration issues

## How It Works

### Application Configuration
1. Environment variables are loaded from `.env` file
2. Docker Compose passes these to the ecPolyCommand service
3. Spring Boot Config Server provides configuration via `ecPolyCommand.properties`
4. `StripeConfigProperties` class loads the configuration
5. `StripeServiceImpl` uses the configuration for Stripe API calls

### Stripe CLI Service
1. Checks if STRIPE_API_KEY is properly configured
2. If not configured: displays warning and waits (prevents timeout)
3. If configured: starts Stripe CLI listener for webhooks
4. Forwards webhooks to `ecPolyCommand` service at `/stripe/webhook`

## Testing the Setup

1. Update `.env` file with your actual Stripe keys
2. Run Docker Compose:
   ```bash
   docker-compose up -d
   ```
3. Check logs for Stripe configuration:
   ```bash
   docker logs ecPolyCommand
   docker logs stripe
   ```
4. Look for successful Stripe API key loading messages

## Troubleshooting

### If you see "WARNING: Stripe API key not configured"
- Check your `.env` file has the correct keys
- Ensure keys are not placeholder values
- Restart the services: `docker-compose restart ecPolyCommand stripe`

### If Stripe CLI still fails
- Verify your Stripe account is active
- Check that API keys have proper permissions
- Ensure webhook endpoint is correctly configured in Stripe Dashboard

### If webhooks aren't working
- Verify the webhook URL in Stripe Dashboard points to your application
- Check that the webhook secret matches your configuration
- Review application logs for webhook processing errors

## Security Notes

- Never commit actual API keys to version control
- Use test keys for development
- Use live keys only in production with proper security measures
- Regularly rotate your API keys
- Monitor Stripe Dashboard for unusual activity

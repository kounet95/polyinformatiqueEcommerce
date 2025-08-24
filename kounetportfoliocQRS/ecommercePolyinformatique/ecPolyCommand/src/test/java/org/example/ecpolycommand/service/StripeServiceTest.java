package org.example.ecpolycommand.service;

import org.example.ecpolycommand.config.StripeConfigProperties;
import org.example.ecpolycommand.service.imple.StripeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StripeServiceTest {

    private StripeServiceImpl stripeService;
    private StripeConfigProperties config;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private PrintStream originalErr;

    @BeforeEach
    void setUp() {
        config = new StripeConfigProperties();
        stripeService = new StripeServiceImpl(config);

        // Capture System.out and System.err for testing log messages
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        originalErr = System.err;
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(outputStream));
    }

    @Test
    void testLogStripeKey_WithValidConfiguration() {
        // Arrange
        StripeConfigProperties.Api api = new StripeConfigProperties.Api();
        api.setKey("sk_test_123456789012345678901234");
        config.setApi(api);

        StripeConfigProperties.Webhook webhook = new StripeConfigProperties.Webhook();
        webhook.setSecret("whsec_123456789012345678901234");
        config.setWebhook(webhook);

        // Act
        stripeService.logStripeKey();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Stripe API Key loaded from config: sk_tes********"));
        assertTrue(output.contains("Stripe Webhook secret loaded: whsec_********"));
        assertFalse(output.contains("WARNING: Stripe API key is not configured"));
        assertFalse(output.contains("WARNING: Stripe webhook secret is not configured"));
    }

    @Test
    void testLogStripeKey_WithMissingApiKey() {
        // Arrange - no API key set
        StripeConfigProperties.Webhook webhook = new StripeConfigProperties.Webhook();
        webhook.setSecret("whsec_123456789012345678901234");
        config.setWebhook(webhook);

        // Act
        stripeService.logStripeKey();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Stripe API Key loaded from config: <null>"));
        assertTrue(output.contains("WARNING: Stripe API key is not configured"));
    }

    @Test
    void testLogStripeKey_WithMissingWebhookSecret() {
        // Arrange
        StripeConfigProperties.Api api = new StripeConfigProperties.Api();
        api.setKey("sk_test_123456789012345678901234");
        config.setApi(api);
        // no webhook secret set

        // Act
        stripeService.logStripeKey();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Stripe Webhook secret loaded: <null>"));
        assertTrue(output.contains("WARNING: Stripe webhook secret is not configured"));
    }

    @Test
    void testLogStripeKey_WithEmptyKeys() {
        // Arrange
        StripeConfigProperties.Api api = new StripeConfigProperties.Api();
        api.setKey("");
        config.setApi(api);

        StripeConfigProperties.Webhook webhook = new StripeConfigProperties.Webhook();
        webhook.setSecret("");
        config.setWebhook(webhook);

        // Act
        stripeService.logStripeKey();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("WARNING: Stripe API key is not configured"));
        assertTrue(output.contains("WARNING: Stripe webhook secret is not configured"));
    }

    @Test
    void testConfigurationProperties() {
        // Test that StripeConfigProperties works correctly
        StripeConfigProperties config = new StripeConfigProperties();

        StripeConfigProperties.Api api = new StripeConfigProperties.Api();
        api.setKey("test-key");
        api.setSecretKey("test-secret");
        config.setApi(api);

        StripeConfigProperties.Webhook webhook = new StripeConfigProperties.Webhook();
        webhook.setSecret("test-webhook-secret");
        config.setWebhook(webhook);

        assertEquals("test-key", config.getApi().getKey());
        assertEquals("test-secret", config.getApi().getSecretKey());
        assertEquals("test-webhook-secret", config.getWebhook().getSecret());
    }

    void tearDown() {
        // Restore original System.out and System.err
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}

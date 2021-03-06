package com.wire.bots.sdk.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import com.wire.bots.sdk.crypto.Crypto;
import com.wire.bots.sdk.factories.CryptoFactory;
import com.wire.bots.sdk.tools.Logger;

import java.util.UUID;

public class CryptoHealthCheck extends HealthCheck {
    private final CryptoFactory cryptoFactory;

    public CryptoHealthCheck(CryptoFactory cryptoFactory) {
        this.cryptoFactory = cryptoFactory;
    }

    @Override
    protected Result check() {
        try {
            Logger.debug("Starting CryptoHealthCheck healthcheck");

            try (Crypto crypto = cryptoFactory.create(UUID.randomUUID().toString())) {
                crypto.newLastPreKey();
                crypto.newPreKeys(0, 8);
                return Result.healthy();
            }
        } catch (Exception e) {
            return Result.unhealthy(e.getMessage());
        } finally {
            Logger.debug("Finished CryptoHealthCheck healthcheck");
        }
    }
}

package org.example.subscriptions.service.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.example.subscriptions.api.model.subscription.CreateSubscription;

public class SubscriptionTestHelper {
    public static CreateSubscription generateCreateSubscription() {
        Instant now = Instant.now();
        return new CreateSubscription(
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(20),
                generateRandomInstant(now.minus(30, ChronoUnit.DAYS), now),
                generateRandomInstant(now, now.plus(365, ChronoUnit.DAYS))
        );
    }

    public static Instant generateRandomInstant(Instant startInclusive, Instant endExclusive) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long randomSeconds = RandomUtils.nextLong(startSeconds, endSeconds);

        return Instant.ofEpochSecond(randomSeconds)
                .plusNanos(RandomUtils.nextLong(0, 1_000_000_000));
    }
}

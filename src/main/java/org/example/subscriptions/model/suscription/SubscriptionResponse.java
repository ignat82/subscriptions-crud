package org.example.subscriptions.model.suscription;

import java.time.Instant;
import java.util.UUID;

public record SubscriptionResponse(
        String title,
        String description,
        Instant startDate,
        Instant endDate,
        UUID userId

) {
    public static SubscriptionResponse from(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.title(),
                subscription.description(),
                subscription.startDate(),
                subscription.endDate(),
                subscription.userId()
        );
    }
}
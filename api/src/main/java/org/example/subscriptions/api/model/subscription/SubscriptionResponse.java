package org.example.subscriptions.api.model.subscription;

import java.time.Instant;
import java.util.UUID;

public record SubscriptionResponse(
        String title,
        String description,
        Instant startDate,
        Instant endDate,
        UUID userId

) {}

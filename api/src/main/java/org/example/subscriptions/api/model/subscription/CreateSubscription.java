package org.example.subscriptions.api.model.subscription;

import java.time.Instant;

public record CreateSubscription(
        String title,
        String description,
        Instant startDate,
        Instant endDate
) {}

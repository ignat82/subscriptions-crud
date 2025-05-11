package org.example.subscriptions.model.suscription;

import java.time.Instant;

public record CreateSubscription(
        String title,
        String description,
        Instant startDate,
        Instant endDate
) {}

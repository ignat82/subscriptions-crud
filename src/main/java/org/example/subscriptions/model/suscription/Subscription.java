package org.example.subscriptions.model.suscription;

import java.time.Instant;
import java.util.UUID;

public record Subscription(
        UUID id,
        String title,
        String description,
        Instant startDate,
        Instant endDate,
        UUID userId,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {}
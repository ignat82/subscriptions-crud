package org.example.subscriptions.model.user;

import java.time.Instant;
import java.util.UUID;

public record User(
        UUID id,
        String username,
        String firstName,
        String middleName,
        String lastName,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {}

package org.example.subscriptions.api.model.user;

public record UserResponse(
        String username,
        String firstName,
        String middleName,
        String lastName
) { }

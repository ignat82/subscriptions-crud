package org.example.subscriptions.api.model.user;

public record CreateUser(
        String username,
        String firstName,
        String middleName,
        String lastName
) {}

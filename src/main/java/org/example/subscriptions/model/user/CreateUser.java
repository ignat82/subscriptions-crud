package org.example.subscriptions.model.user;

public record CreateUser(
        String username,
        String firstName,
        String middleName,
        String lastName
) {}

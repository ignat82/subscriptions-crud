package org.example.subscriptions.model.user;

public record UserResponse(
        String username,
        String firstName,
        String middleName,
        String lastName
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.username(),
                user.firstName(),
                user.middleName(),
                user.lastName()
        );
    }
}

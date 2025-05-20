package org.example.subscriptions.service.mapper;

import org.example.subscriptions.api.model.user.UserResponse;
import org.example.subscriptions.service.model.User;

public class UserMapper {

    public static UserResponse createUserResponse(User user) {
        return new UserResponse(
                user.username(),
                user.firstName(),
                user.middleName(),
                user.lastName()
        );
    }
}

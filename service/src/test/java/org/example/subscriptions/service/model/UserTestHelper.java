package org.example.subscriptions.service.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.subscriptions.api.model.user.CreateUser;
import org.example.subscriptions.api.model.user.UpdateUser;

public class UserTestHelper {
    public static CreateUser generateCreateUser() {
        return new CreateUser(
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10)
        );
    }

    public static UpdateUser generateUpdateUser() {
        return new UpdateUser(
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphanumeric(10)
        );
    }
}

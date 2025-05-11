package org.example.subscriptions.model.user;

import org.apache.commons.lang3.RandomStringUtils;

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

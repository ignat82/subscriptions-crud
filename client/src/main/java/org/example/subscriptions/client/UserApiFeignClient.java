package org.example.subscriptions.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import java.util.List;
import java.util.UUID;
import org.example.subscriptions.api.SubscriptionApi;
import org.example.subscriptions.api.UserApi;
import org.example.subscriptions.api.model.subscription.CreateSubscription;
import org.example.subscriptions.api.model.subscription.SubscriptionResponse;
import org.example.subscriptions.api.model.subscription.SubscriptionStatistic;
import org.example.subscriptions.api.model.user.CreateUser;
import org.example.subscriptions.api.model.user.UpdateUser;
import org.example.subscriptions.api.model.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

/**
 * Feign интерфейс для взаимодействия с сервисом SubscriptionsService
 */
@FeignClient("subscriptions-service-user")
@Headers("Content-Type: application/json")
public interface UserApiFeignClient extends UserApi {
    @RequestLine("POST /users")
    @Override
    ResponseEntity<UserResponse> createUser(
            CreateUser createUser
    );

    @RequestLine("GET /users/{id}")
    @Override
    ResponseEntity<UserResponse> getUser(
            @Param("id") UUID id
    );

    @RequestLine("PUT /users/{id}")
    @Override
    ResponseEntity<UserResponse> updateUser(
            @Param("id") UUID id,
            UpdateUser updateUser
    );

    @RequestLine("DELETE /users/{id}")
    @Override
    void deleteUser(
            @Param("id") UUID id
    );
}
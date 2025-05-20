package org.example.subscriptions.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import java.util.List;
import java.util.UUID;
import org.example.subscriptions.api.SubscriptionsServiceApi;
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
@FeignClient("subscriptions-service")
@Headers("Content-Type: application/json")
public interface SubscriptionsServiceApiFeignClient extends SubscriptionsServiceApi {
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

    @RequestLine("POST /users/{userId}/subscriptions")
    @Override
    ResponseEntity<SubscriptionResponse> createSubscription(
            @Param UUID userId,
            CreateSubscription createSubscription
    );

    @RequestLine("GET /users/{userId}/subscriptions")
    @Override
    ResponseEntity<List<SubscriptionResponse>> getUserSubscriptions(
            @Param UUID userId
    );

    @RequestLine("DELETE /users/{userId}/subscriptions/{subscriptionId}")
    @Override
    void deleteSubscription(
            @Param UUID userId,
            UUID subscriptionId
    );

    @RequestLine("GET /subscriptions/top")
    @Override
    ResponseEntity<List<SubscriptionStatistic>> getTopSubscriptions();
}
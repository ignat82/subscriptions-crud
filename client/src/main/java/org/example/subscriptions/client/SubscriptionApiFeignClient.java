package org.example.subscriptions.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import java.util.List;
import java.util.UUID;
import org.example.subscriptions.api.SubscriptionApi;
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
@FeignClient("subscriptions-service-subscription")
@Headers("Content-Type: application/json")
public interface SubscriptionApiFeignClient extends SubscriptionApi {

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
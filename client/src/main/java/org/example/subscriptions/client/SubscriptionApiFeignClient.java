package org.example.subscriptions.client;

import feign.Headers;
import java.util.List;
import java.util.UUID;
import org.example.subscriptions.api.SubscriptionApi;
import org.example.subscriptions.api.model.subscription.CreateSubscription;
import org.example.subscriptions.api.model.subscription.SubscriptionResponse;
import org.example.subscriptions.api.model.subscription.SubscriptionStatistic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Feign интерфейс для взаимодействия с сервисом SubscriptionsService
 */
@FeignClient("subscriptions-service-subscription")
@Headers("Content-Type: application/json")
public interface SubscriptionApiFeignClient extends SubscriptionApi {

    @PostMapping("/users/{userId}/subscriptions")
    @Override
    ResponseEntity<SubscriptionResponse> createSubscription(
            @PathVariable UUID userId,
            CreateSubscription createSubscription
    );

    @GetMapping("/users/{userId}/subscriptions")
    @Override
    ResponseEntity<List<SubscriptionResponse>> getUserSubscriptions(
            @PathVariable UUID userId
    );

    @DeleteMapping("/users/{userId}/subscriptions/{subscriptionId}")
    @Override
    void deleteSubscription(
            @PathVariable UUID userId,
            UUID subscriptionId
    );

    @GetMapping("/subscriptions/top")
    @Override
    ResponseEntity<List<SubscriptionStatistic>> getTopSubscriptions();
}
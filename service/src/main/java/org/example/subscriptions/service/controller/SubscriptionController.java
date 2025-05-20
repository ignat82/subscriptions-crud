package org.example.subscriptions.service.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.example.subscriptions.api.model.subscription.CreateSubscription;
import org.example.subscriptions.api.model.subscription.SubscriptionResponse;
import org.example.subscriptions.api.model.subscription.SubscriptionStatistic;
import org.example.subscriptions.service.service.SubscriptionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SubscriptionController {

    private final SubscriptionManager subscriptionManager;

    public SubscriptionController(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    @PostMapping("/users/{userId}/subscriptions")
    public ResponseEntity<SubscriptionResponse> createSubscription(
            @PathVariable UUID userId,
            @RequestBody @Valid CreateSubscription createSubscription
    ) {
        SubscriptionResponse subscription = subscriptionManager.createSubscription(
                userId,
                createSubscription
        ).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Subscription was not created")
        );
        return ResponseEntity.ok(subscription);
    }

    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<List<SubscriptionResponse>> getUserSubscriptions(
            @PathVariable UUID userId
    ) {
        List<SubscriptionResponse> subscriptions = subscriptionManager.getUserSubscriptions(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/users/{userId}/subscriptions/{subscriptionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(
            @PathVariable UUID userId,
            @PathVariable UUID subscriptionId) {
        if (!subscriptionManager.deleteSubscription(userId, subscriptionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscription not found");
        }
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<SubscriptionStatistic>> getTopSubscriptions() {
        List<SubscriptionStatistic> topSubscriptions = subscriptionManager.getTop3Subscriptions();
        return ResponseEntity.ok(topSubscriptions);
    }
}

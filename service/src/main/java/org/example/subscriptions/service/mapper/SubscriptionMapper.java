package org.example.subscriptions.service.mapper;

import org.example.subscriptions.api.model.subscription.SubscriptionResponse;
import org.example.subscriptions.service.model.Subscription;

public class SubscriptionMapper {

    public static SubscriptionResponse createSubscriptionResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.title(),
                subscription.description(),
                subscription.startDate(),
                subscription.endDate(),
                subscription.userId()
        );
    }
}

package org.example.subscriptions.api;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.example.subscriptions.api.model.subscription.CreateSubscription;
import org.example.subscriptions.api.model.subscription.SubscriptionResponse;
import org.example.subscriptions.api.model.subscription.SubscriptionStatistic;
import org.springframework.http.ResponseEntity;

/**
 * API для управления подписками пользователей
 */
@Tag(
        name = "SubscriptionAPI",
        description = "API для управления подписками пользователя"
)
public interface SubscriptionApi {

    @Operation(
            summary = "Создать подписку для пользователя",
            method = "POST",
            externalDocs = @ExternalDocumentation(
                    description = "Документация API",
                    url = "https://example.com/api/docs/subscriptions"
            )
    )
    ResponseEntity<SubscriptionResponse> createSubscription(
            UUID userId,
            CreateSubscription createSubscription
    );

    @Operation(
            summary = "Получить все подписки пользователя",
            method = "GET",
            externalDocs = @ExternalDocumentation(
                    description = "Документация API",
                    url = "https://example.com/api/docs/user-subscriptions"
            )
    )
    ResponseEntity<List<SubscriptionResponse>> getUserSubscriptions(
            UUID userId
    );

    @Operation(
            summary = "Удалить подписку пользователя",
            method = "DELETE",
            externalDocs = @ExternalDocumentation(
                    description = "Документация API",
                    url = "https://example.com/api/docs/delete-subscription"
            )
    )
    void deleteSubscription(
            UUID userId,
            UUID subscriptionId
    );

    @Operation(
            summary = "Получить топ популярных подписок",
            method = "GET",
            externalDocs = @ExternalDocumentation(
                    description = "Документация API",
                    url = "https://example.com/api/docs/top-subscriptions"
            )
    )
    ResponseEntity<List<SubscriptionStatistic>> getTopSubscriptions();
}
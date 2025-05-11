package org.example.subscriptions.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.example.subscriptions.dao.SubscriptionDAO;
import org.example.subscriptions.dao.UserDAO;
import org.example.subscriptions.model.suscription.CreateSubscription;
import org.example.subscriptions.model.suscription.Subscription;
import org.example.subscriptions.model.suscription.SubscriptionResponse;
import org.example.subscriptions.model.suscription.SubscriptionStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SubscriptionManager {
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private final SubscriptionDAO subscriptionDAO;
    private final UserDAO userDAO;
    @Value("${most-popular-subscriptions-request-limit}")
    private int mostPopularLimit;

    public SubscriptionManager(
            SubscriptionDAO subscriptionDAO,
            UserDAO userDAO
    ) {
        this.subscriptionDAO = subscriptionDAO;
        this.userDAO = userDAO;
    }
    public Optional<SubscriptionResponse> createSubscription(
            UUID userId,
            CreateSubscription createSubscription
    ) {
        logger.debug("createSubscription(createSubscription: {})", createSubscription);

        if (createSubscription.startDate().isAfter(createSubscription.endDate())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Start date must be before end date"
            );
        }
        validateUser(userId);

        Optional<Subscription> subscription = subscriptionDAO.createSubscription(userId, createSubscription);
        subscription.ifPresentOrElse(
                s -> logger.info("subscription created: {}", subscription),
                () -> logger.warn("subscription was not created. Something unexpected happens. Might be concurrency issue")
        );
        return subscription.map(SubscriptionResponse::from);
    }

    private void validateUser(UUID userId) {
        userDAO.findUserById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found: " + userId
                ));
    }

    public Optional<List<SubscriptionResponse>> getUserSubscriptions(UUID userId) {
        logger.debug("getUserSubscriptions(userId: {})", userId);

        validateUser(userId);

        Optional<List<Subscription>> subscriptions = subscriptionDAO.findByUserId(userId);
        subscriptions.ifPresentOrElse(
                s -> logger.info("found subscriptions: {}", s),
                () -> logger.warn("subscriptions acquiring failed. Something unexpected happens. Might be concurrency issue")
        );
        return subscriptions.map(it -> it.stream().map(SubscriptionResponse::from).toList());
    }

    public boolean deleteSubscription(UUID userId, UUID subscriptionId) {
        logger.debug("deleteSubscription(userId: {}, subscriptionId: {})", userId, subscriptionId);

        validateUser(userId);

        Optional<Subscription> subscription = subscriptionDAO.deleteSubscription(subscriptionId);
        subscription.ifPresentOrElse(
                s -> logger.info("subscription deleted: {}", subscription),
                () -> logger.warn("subscription was not deleted. Something unexpected happens. Might be concurrency issue")
        );
        return subscription.isPresent();
    }

    public List<SubscriptionStatistic> getTop3Subscriptions() {
        logger.debug("getTop3Subscriptions()");
        List<SubscriptionStatistic> subscriptionStatistics = subscriptionDAO.findMostPopular(mostPopularLimit);
        logger.info("got {} most popular subscriptions: {}", mostPopularLimit, subscriptionStatistics);
        return subscriptionStatistics;
    }
}

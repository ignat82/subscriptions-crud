package org.example.subscriptions.controller;

import java.util.UUID;
import org.example.subscriptions.AbstractIntegrationTest;
import org.example.subscriptions.dao.SubscriptionDAO;
import org.example.subscriptions.model.suscription.CreateSubscription;
import org.example.subscriptions.model.suscription.Subscription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import static org.example.subscriptions.model.user.SubscriptionTestHelper.generateCreateSubscription;
import static org.example.subscriptions.model.user.UserTestHelper.generateCreateUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubscriptionControllerTest extends AbstractIntegrationTest {

    @Autowired
    private SubscriptionDAO subscriptionDAO;

    @Value("${most-popular-subscriptions-request-limit}")
    private int mostPopularLimit;

    @Test
    void createSubscription() throws Exception {
        // given
        UUID userId = userDAO.createUser(generateCreateUser()).id();
        CreateSubscription createSubscription = generateCreateSubscription();

        // when
        mockMvc.perform(post("/users/{userId}/subscriptions", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSubscription)))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(createSubscription.title()))
                .andExpect(jsonPath("$.description").value(createSubscription.description()))
                .andExpect(jsonPath("$.userId").value(userId.toString()));
    }

    @Test
    void createSubscription_userNotFound() throws Exception {
        // given
        UUID nonExistentUserId = UUID.randomUUID();
        CreateSubscription createSubscription = generateCreateSubscription();

        // when
        mockMvc.perform(post("/users/{userId}/subscriptions", nonExistentUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSubscription)))

        // then
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserSubscriptions() throws Exception {
        // given
        UUID userId = userDAO.createUser(generateCreateUser()).id();
        CreateSubscription createSubscription = generateCreateSubscription();

        subscriptionDAO.createSubscription(userId, createSubscription);

        // when
        mockMvc.perform(get("/users/{userId}/subscriptions", userId))
                .andExpect(status().isOk())

        // then
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value(createSubscription.title()))
                .andExpect(jsonPath("$[0].description").value(createSubscription.description()))
                .andExpect(jsonPath("$[0].userId").value(userId.toString()));
    }

    @Test
    void getUserSubscriptions_userNotFound() throws Exception {
        // given
        UUID nonExistentUserId = UUID.randomUUID();

        // when
        mockMvc.perform(get("/users/{userId}/subscriptions", nonExistentUserId))

        // then
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSubscription() throws Exception {
        // given
        UUID userId = userDAO.createUser(generateCreateUser()).id();
        Subscription subscription = subscriptionDAO.createSubscription(userId, generateCreateSubscription())
                .get();

        // when
        mockMvc.perform(delete("/users/{userId}/subscriptions/{subscriptionId}", userId, subscription.id()))

        // then
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSubscription_subscriptionNotFound() throws Exception {
        // given
        UUID userId = userDAO.createUser(generateCreateUser()).id();
        UUID nonExistentSubscriptionId = UUID.randomUUID();

        // when
        mockMvc.perform(delete("/users/{userId}/subscriptions/{subscriptionId}", userId, nonExistentSubscriptionId))

        // then
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSubscription_userNotFound() throws Exception {
        // given
        UUID userId = userDAO.createUser(generateCreateUser()).id();
        Subscription subscription = subscriptionDAO.createSubscription(userId, generateCreateSubscription())
                .get();
        UUID nonExistingUserId = UUID.randomUUID();

        // when
        mockMvc.perform(delete("/users/{userId}/subscriptions/{subscriptionId}", nonExistingUserId, subscription.id()))

                // then
                .andExpect(status().isNotFound());
    }

    @Test
    void getTopSubscriptions() throws Exception {
        // given
        UUID userId1 = userDAO.createUser(generateCreateUser()).id();
        UUID userId2 = userDAO.createUser(generateCreateUser()).id();
        UUID userId3 = userDAO.createUser(generateCreateUser()).id();

        CreateSubscription createSubscription1 = generateCreateSubscription();
        CreateSubscription createSubscription2 = generateCreateSubscription();
        CreateSubscription createSubscription3 = generateCreateSubscription();
        CreateSubscription createSubscription4 = generateCreateSubscription();

        subscriptionDAO.createSubscription(userId1, createSubscription1);
        subscriptionDAO.createSubscription(userId2, createSubscription1);
        subscriptionDAO.createSubscription(userId3, createSubscription1);

        subscriptionDAO.createSubscription(userId2, createSubscription2);
        subscriptionDAO.createSubscription(userId3, createSubscription2);

        subscriptionDAO.createSubscription(userId1, createSubscription3);
        subscriptionDAO.createSubscription(userId3, createSubscription3);

        subscriptionDAO.createSubscription(userId2, createSubscription4);


        // when
        mockMvc.perform(get("/subscriptions/top"))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(mostPopularLimit))
                .andExpect(jsonPath("$[0].subscribers").value(3))
                .andExpect(jsonPath("$[0].subscriptionTitle").value(createSubscription1.title()));
    }

}

package org.example.subscriptions.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import java.util.UUID;
import org.example.subscriptions.api.UserApi;
import org.example.subscriptions.api.model.user.CreateUser;
import org.example.subscriptions.api.model.user.UpdateUser;
import org.example.subscriptions.api.model.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Feign интерфейс для взаимодействия с сервисом SubscriptionsService
 */
@FeignClient("subscriptions-service-user")
@Headers("Content-Type: application/json")
public interface UserApiFeignClient extends UserApi {
    @PostMapping("/users")
    @Override
    ResponseEntity<UserResponse> createUser(
            CreateUser createUser
    );

    @GetMapping("/users/{id}")
    @Override
    ResponseEntity<UserResponse> getUser(
            @PathVariable("id") UUID id
    );

    @PutMapping("/users/{id}")
    @Override
    ResponseEntity<UserResponse> updateUser(
            @PathVariable("id") UUID id,
            UpdateUser updateUser
    );

    @DeleteMapping("/users/{id}")
    @Override
    void deleteUser(
            @PathVariable("id") UUID id
    );
}
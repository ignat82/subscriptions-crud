package org.example.subscriptions.api;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.example.subscriptions.api.model.user.CreateUser;
import org.example.subscriptions.api.model.user.UpdateUser;
import org.example.subscriptions.api.model.user.UserResponse;
import org.springframework.http.ResponseEntity;

/**
 * API для управления подписками пользователей
 */
@Tag(
        name = "UserAPI",
        description = "API для управления пользователями"
)
public interface UserApi {

    @Operation(
            summary = "Создать пользователя",
            description = "Регистрирует нового пользователя в системе",
            method = "POST",
            externalDocs = @ExternalDocumentation(
                    description = "Документация API",
                    url = "https://example.com/api/docs/create-user"
            )
    )
    ResponseEntity<UserResponse> createUser(
            CreateUser createUser
    );

    @Operation(
            summary = "Получить данные пользователя",
            description = "Возвращает информацию о пользователе по ID",
            method = "GET",
            externalDocs = @ExternalDocumentation(
                    description = "Документация API",
                    url = "https://example.com/api/docs/get-user"
            )
    )
    ResponseEntity<UserResponse> getUser(
            UUID id
    );

    @Operation(
            summary = "Обновить данные пользователя",
            description = "Изменяет информацию о пользователе",
            method = "PUT",
            externalDocs = @ExternalDocumentation(
                    description = "Документация API",
                    url = "https://example.com/api/docs/update-user"
            )
    )
    ResponseEntity<UserResponse> updateUser(
            UUID id,
            UpdateUser updateUser
    );

    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя из системы",
            method = "DELETE",
            externalDocs = @ExternalDocumentation(
                    description = "Документация API",
                    url = "https://example.com/api/docs/delete-user"
            )
    )
    void deleteUser(
            UUID id
    );
}
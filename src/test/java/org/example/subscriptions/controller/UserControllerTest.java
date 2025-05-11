package org.example.subscriptions.controller;

import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.subscriptions.AbstractIntegrationTest;
import org.example.subscriptions.model.user.CreateUser;
import org.example.subscriptions.model.user.UpdateUser;
import org.example.subscriptions.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.example.subscriptions.model.user.UserTestHelper.generateCreateUser;
import static org.example.subscriptions.model.user.UserTestHelper.generateUpdateUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractIntegrationTest {

    @Test
    void createUser() throws Exception {
        // given
        CreateUser createUser = generateCreateUser();

        // when
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUser)))

        // then
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username").value(createUser.username()))
                .andExpect(jsonPath("$.firstName").value(createUser.firstName()))
                .andExpect(jsonPath("$.lastName").value(createUser.lastName()));
    }

    @Test
    void getUser() throws Exception {
        // given
        User user = userDAO.createUser(generateCreateUser());

        // when
        mockMvc.perform(get("/users/{id}", user.id()))

        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.username()))
                .andExpect(jsonPath("$.firstName").value(user.firstName()))
                .andExpect(jsonPath("$.middleName").value(user.middleName()))
                .andExpect(jsonPath("$.lastName").value(user.lastName()));
    }

    @Test
    void getUser_notFound() throws Exception {
        // given
        UUID nonExistentId = UUID.randomUUID();

        // when
        mockMvc.perform(get("/users/{id}", nonExistentId))

        // then
                .andExpect(status().isNotFound());
    }

    @Test
    void getUser_badRequest() throws Exception {
        // given
        String incorrectUuid = RandomStringUtils.randomAlphanumeric(10);

        // when
        mockMvc.perform(get("/users/{id}", incorrectUuid))

        // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser() throws Exception {
        // given
        User user = userDAO.createUser(generateCreateUser());

        UpdateUser updateUser = generateUpdateUser();

        //when
        mockMvc.perform(put("/users/{id}", user.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUser)))

        // then
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.username").value(user.username()))
                .andExpect(jsonPath("$.firstName").value(updateUser.firstName()))
                .andExpect(jsonPath("$.middleName").value(updateUser.middleName()))
                .andExpect(jsonPath("$.lastName").value(updateUser.lastName()));
    }

    @Test
    void updateUser_notFound() throws Exception {
        // given
        UUID nonExistentId = UUID.randomUUID();

        // when
        mockMvc.perform(put("/users/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateCreateUser())))

        // then
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser() throws Exception {
        // given
        User user = userDAO.createUser(generateCreateUser());

        // when
        mockMvc.perform(delete("/users/{id}", user.id()))

        // then
                .andExpect(status().isNoContent());

        assertThat(userDAO.findUserById(user.id())).isEmpty();
    }

    @Test
    void deleteUser_notFound() throws Exception {
        // given
        UUID nonExistentId = UUID.randomUUID();

        // when
        mockMvc.perform(delete("/users/{id}", nonExistentId))

        // then
                .andExpect(status().isNotFound());
    }

}

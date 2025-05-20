package org.example.subscriptions.service.service;

import java.util.Optional;
import java.util.UUID;
import org.example.subscriptions.service.dao.UserDAO;
import org.example.subscriptions.api.model.user.CreateUser;
import org.example.subscriptions.api.model.user.UpdateUser;
import org.example.subscriptions.service.mapper.UserMapper;
import org.example.subscriptions.service.model.User;
import org.example.subscriptions.api.model.user.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserManager {
    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private final UserDAO userDAO;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public UserResponse createUser(CreateUser createUser) {
        logger.debug("createUser(createUser: {})", createUser);
        User user = userDAO.createUser(createUser);
        logger.info("created new user: {}", user);
        return UserMapper.createUserResponse(user);
    }

    public Optional<UserResponse> getUser(UUID id) {
        logger.debug("getUser(id: {})", id);
        Optional<User> user = userDAO.findUserById(id);
        user.ifPresentOrElse(
                u -> logger.info("Found user: {}", u),
                () -> logger.warn("user with id {} not found.  Is userId wrong or user deleted?", id)
        );
        return user.map(UserMapper::createUserResponse);
    }

    public Optional<UserResponse> updateUser(UUID id, UpdateUser updateUser) {
        logger.debug("updateUser(id: {}, updateUser: {})", id, updateUser);
        Optional<User> user = userDAO.updateUser(id, updateUser);
        user.ifPresentOrElse(
                u -> logger.info("Updated user: {}", u),
                () -> logger.warn("user with id {} not found. Is userId wrong or user deleted?", id)
        );
        return user.map(UserMapper::createUserResponse);
    }

    public Optional<UserResponse> deleteUser(UUID id) {
        logger.debug("deleteUser(id: {})", id);
        Optional<User> user = userDAO.deleteUser(id);
        user.ifPresentOrElse(
                u -> logger.info("Deleted user: {}", u),
                () -> logger.warn("user with id {} not found. Is userId wrong or user deleted?", id)
        );
        return user.map(UserMapper::createUserResponse);
    }
}

package org.example.subscriptions.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.subscriptions.service.dao.UserDAO;
import org.example.subscriptions.service.dao.UserTestDAO;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {
    @LocalServerPort
    int port = -1;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserDAO userDAO;

    @Autowired
    protected UserTestDAO userTestDAO;

    protected static final PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>("postgres:15-alpine")
                .withStartupTimeout(java.time.Duration.ofSeconds(60));
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("logging.level.org.springframework.jdbc", () -> "DEBUG");
        registry.add("logging.level.org.testcontainers", () -> "DEBUG");
        registry.add("spring.test.execution.parallel.enabled", () -> "false");
    }

    @AfterEach
    void cleanDb() {
        userTestDAO.truncateTableUsers();
    }

}

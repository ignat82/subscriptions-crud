# Overal project description

Subscription Management Service CRUD microservice.
Microservice is built with Spring Boot 3 that provides REST API endpoints for managing users and their digital service subscriptions 
(such as YouTube Premium, Netflix, etc.). 
The application has comprehensive test coverage.

# Tech Stack

Backend: Java 17, Spring Boot 3.2.0
API Documentation: OpenAPI/Swagger
Database: PostgreSQL with Liquibase for database migrations
Build Tool: Gradle with Kotlin DSL
Testing: JUnit 5, Spring Boot Test, Testcontainers
Logging: SLF4J
Containerization: Docker with multi-stage builds, Docker Compose for local development and deployment
Client: Spring Cloud OpenFeign client module

# Project structure
subscriptions-crud/
├── api/                      # API contracts and DTOs
│   └── src/main/java/org/example/subscriptions/api
│       ├── model/            # Data transfer objects
│       └── SubscriptionsServiceApi.java
│
├── client/                   # Feign client implementation
│   └── src/main/java/org/example/subscriptions/client
│       └── SubscriptionsServiceApiFeignClient.java
│
├── service/                  # Main service implementation
│   ├── controller/           # REST controllers
│   ├── service/              # Business logic
│   ├── dao/                  # Data access
│   └── mapper/               # Entity-DTO mapping
│
└── resources/
├── liquibase/            # Database migrations
└── application.yml       # Configuration

# Features

Complete user management (CRUD operations)
Subscription management for digital services
Statistics for popular subscriptions
Feign client for service-to-service communication
API documentation
Soft deletion pattern implemented for data integrity
RESTful API with proper response handling
Database integration with PostgreSQL
Comprehensive test coverage with integration tests using Testcontainers
Dockerized deployment

# Project Structure

The codebase follows a standard Spring Boot application structure with clear separation of concerns:

Controllers for handling HTTP requests
Service layer for business logic
DAO layer for database operations
Model classes for domain entities
Exception handlers for graceful error handling

# Getting Started

Running Locally with Docker Compose
```
# Clone the repository
git clone <repository-url>
cd subscription-service

# Start the application and database with Docker Compose
docker-compose up

# Access API
http://localhost:8080/swagger-ui.html
```

# API Endpoints

## Users:

- POST /users - Create a user
- GET /users/{id} - Get user information
- PUT /users/{id} - Update user
- DELETE /users/{id} - Delete user

## Subscriptions:

- POST /users/{id}/subscriptions - Add subscription
- GET /users/{id}/subscriptions - Get user subscriptions
- DELETE /users/{id}/subscriptions/{sub_id} - Delete subscription
- GET /subscriptions/top - Get top 3 popular subscriptions


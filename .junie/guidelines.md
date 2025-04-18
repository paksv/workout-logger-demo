# Workout Logger Demo - Development Guidelines

This document provides guidelines and best practices for developing the Workout Logger Demo application.

Use `docs/plan.md` to plan as the implementation guide for this project.

## Technology Stack

- **Backend**: Kotlin with Spring Boot
- **Database**: PostgreSQL (or H2 for development)
- **API**: RESTful API with Spring Web
- **Frontend**: React or Kotlin Multiplatform with Compose for Web/Desktop/Mobile
- **Authentication**: Spring Security with JWT
- **Build Tool**: Gradle with Kotlin DSL

## Kotlin Style Guide

### Naming Conventions
- **Classes and Objects**: Use PascalCase (e.g., `WorkoutService`, `ExerciseRepository`)
- **Functions and Properties**: Use camelCase (e.g., `getUserWorkouts()`, `totalCaloriesBurned`)
- **Constants**: Use UPPER_SNAKE_CASE (e.g., `MAX_WORKOUT_DURATION`, `DEFAULT_REST_PERIOD`)
- **Package Names**: Use lowercase with no underscores (e.g., `com.example.workoutloggerdemo.service`)

### Code Structure
- Keep functions small and focused on a single responsibility
- Limit function parameters (consider using data classes for multiple parameters)
- Use expression bodies for simple functions: `fun double(x: Int): Int = x * 2`
- Prefer immutability: use `val` over `var` when possible
- Use nullable types (`String?`) only when a value can legitimately be null

### Kotlin Idioms
- Use string templates: `"Hello, $name"`
- Use `when` expressions instead of complex if-else chains
- Use extension functions to extend functionality of existing classes
- Use data classes for model objects: `data class User(val name: String, val age: Int)`
- Use scope functions (`let`, `apply`, `run`, `with`, `also`) appropriately:
  - `let`: for executing a block with non-null values and transforming results
  - `apply`: for object configuration
  - `run`: for object configuration and computing a result
  - `with`: for calling multiple methods on an object
  - `also`: for performing side effects on an object

### Coroutines
- Use coroutines for asynchronous programming instead of callbacks
- Define appropriate coroutine scopes for different parts of the application
- Use structured concurrency principles
- Handle exceptions properly in coroutines

### Testing
- Write unit tests for all business logic
- Use descriptive test method names that explain the scenario and expected outcome
- Follow the Arrange-Act-Assert pattern in tests

## Spring Boot Best Practices and Architectural Patterns

### Project Structure
Organize code by feature rather than by layer:

```
com.example.workoutloggerdemo
├── workout/
│   ├── WorkoutController.kt
│   ├── WorkoutService.kt
│   ├── WorkoutRepository.kt
│   └── Workout.kt
├── exercise/
│   ├── ExerciseController.kt
│   ├── ExerciseService.kt
│   ├── ExerciseRepository.kt
│   └── Exercise.kt
└── common/
    ├── config/
    ├── exception/
    └── util/
```

### Architectural Patterns

#### Layered Architecture
- **Controller Layer**: Handles HTTP requests and responses
- **Service Layer**: Contains business logic
- **Repository Layer**: Manages data access
- **Domain Layer**: Defines the core business entities

#### Dependency Injection
- Use constructor injection over field injection
- Keep components loosely coupled
- Design for testability

#### RESTful API Design
- Use appropriate HTTP methods (GET, POST, PUT, DELETE)
- Return appropriate HTTP status codes
- Use consistent URL patterns
- Version your APIs

### Spring Boot Specific Practices

#### Configuration
- Use `application.yml` or `application.properties` for configuration
- Use profiles for environment-specific configurations
- Externalize sensitive configuration (credentials, API keys)
- Use `@ConfigurationProperties` for type-safe configuration

#### Database Access
- Use Spring Data repositories when possible
- Consider using Kotlin-specific extensions like Spring Data JDBC with Kotlin
- Use database migrations (Flyway or Liquibase)
- Implement proper transaction management

#### Security
- Always use HTTPS in production
- Implement proper authentication and authorization
- Use Spring Security for securing endpoints
- Validate all user inputs
- Implement proper CORS configuration

#### Error Handling
- Create a global exception handler using `@ControllerAdvice`
- Return consistent error responses
- Log exceptions appropriately
- Don't expose sensitive information in error messages

#### Testing
- Write unit tests for services and repositories
- Write integration tests for controllers
- Use `@SpringBootTest` for integration tests
- Use test slices (`@WebMvcTest`, `@DataJpaTest`) for focused testing
- Use test containers for integration tests with databases

#### Performance
- Use caching where appropriate
- Consider pagination for large result sets
- Use async processing for long-running tasks
- Monitor application performance

### Kotlin-Spring Integration Best Practices
- Use Kotlin's non-nullable types to avoid null pointer exceptions
- Leverage Kotlin's extension functions to enhance Spring components
- Use Kotlin's data classes for DTOs and entity classes
- Take advantage of Kotlin Coroutines for reactive programming

By following these guidelines, we can maintain a consistent, high-quality codebase that is easy to understand, test, and extend.
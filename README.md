# Workout Logger Demo

A RESTful API for tracking workouts, exercises, and fitness progress.

## Features

- User registration and authentication
- Athlete profile management
- Exercise library with predefined and custom exercises
- Workout tracking with sets, reps, weight, duration, and distance
- Search and filter capabilities for workouts and exercises

## Technology Stack

- **Backend**: Kotlin with Spring Boot
- **Database**: PostgreSQL (H2 for development)
- **Security**: Spring Security
- **Build Tool**: Gradle with Kotlin DSL

## API Documentation

### Authentication

#### Register a new user

```
POST /api/users/register
```

Request body:
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123"
}
```

#### Login

```
POST /api/auth/login
```

Request body:
```json
{
  "username": "john_doe",
  "password": "password123"
}
```

### User Management

#### Get all users

```
GET /api/users
```

#### Get user by ID

```
GET /api/users/{id}
```

#### Update user

```
PUT /api/users/{id}
```

Request body:
```json
{
  "username": "john_updated",
  "email": "john_updated@example.com",
  "password": "newpassword123"
}
```

#### Delete user

```
DELETE /api/users/{id}
```

### Athlete Management

#### Get all athletes

```
GET /api/athletes
```

#### Get athlete by ID

```
GET /api/athletes/{id}
```

#### Get athlete by user ID

```
GET /api/athletes/user/{userId}
```

#### Create athlete

```
POST /api/athletes
```

Request body:
```json
{
  "userId": 1,
  "height": 180.5,
  "weight": 75.0,
  "fitnessGoals": "Build muscle and improve endurance"
}
```

#### Update athlete

```
PUT /api/athletes/{id}
```

Request body:
```json
{
  "height": 181.0,
  "weight": 74.5,
  "fitnessGoals": "Maintain weight and improve strength"
}
```

#### Update athlete by user ID

```
PUT /api/athletes/user/{userId}
```

Request body:
```json
{
  "height": 181.0,
  "weight": 74.5,
  "fitnessGoals": "Maintain weight and improve strength"
}
```

#### Delete athlete

```
DELETE /api/athletes/{id}
```

### Exercise Management

#### Get all exercises

```
GET /api/exercises
```

#### Get exercise by ID

```
GET /api/exercises/{id}
```

#### Get exercises by category

```
GET /api/exercises/category/{category}
```

Categories: STRENGTH, CARDIO, FLEXIBILITY, BALANCE

#### Get custom exercises for user

```
GET /api/exercises/user/{userId}/custom
```

#### Get all available exercises for user

```
GET /api/exercises/user/{userId}/available
```

#### Search exercises by name

```
GET /api/exercises/search?searchTerm=push
```

#### Create exercise

```
POST /api/exercises
```

Request body:
```json
{
  "name": "Custom Push-up",
  "description": "A variation of the standard push-up",
  "category": "STRENGTH",
  "muscleGroups": ["CHEST", "SHOULDERS", "TRICEPS"],
  "isCustom": true,
  "userId": 1
}
```

#### Update exercise

```
PUT /api/exercises/{id}
```

Request body:
```json
{
  "name": "Modified Push-up",
  "description": "An updated description",
  "category": "STRENGTH",
  "muscleGroups": ["CHEST", "SHOULDERS", "TRICEPS", "CORE"]
}
```

#### Delete exercise

```
DELETE /api/exercises/{id}
```

### Workout Management

#### Get all workouts

```
GET /api/workouts
```

#### Get workout by ID

```
GET /api/workouts/{id}
```

#### Get workouts by user ID

```
GET /api/workouts/user/{userId}
```

#### Get workouts by date range

```
GET /api/workouts/user/{userId}/date-range?startDate=2023-01-01&endDate=2023-12-31
```

#### Get recent workouts

```
GET /api/workouts/user/{userId}/recent?limit=5
```

#### Search workouts by name

```
GET /api/workouts/search?userId=1&searchTerm=morning
```

#### Create workout

```
POST /api/workouts
```

Request body:
```json
{
  "name": "Morning Routine",
  "userId": 1,
  "date": "2023-05-15",
  "durationSeconds": 1800,
  "notes": "Felt great today"
}
```

#### Update workout

```
PUT /api/workouts/{id}
```

Request body:
```json
{
  "name": "Updated Routine",
  "date": "2023-05-16",
  "durationSeconds": 2000,
  "notes": "Modified workout"
}
```

#### Delete workout

```
DELETE /api/workouts/{id}
```

### Workout Exercise Management

#### Get workout exercises

```
GET /api/workouts/{workoutId}/exercises
```

#### Add exercise to workout

```
POST /api/workouts/{workoutId}/exercises
```

Request body:
```json
{
  "exerciseId": 1,
  "order": 1
}
```

#### Update workout exercise

```
PUT /api/workouts/exercises/{workoutExerciseId}
```

Request body:
```json
{
  "order": 2
}
```

#### Remove exercise from workout

```
DELETE /api/workouts/exercises/{workoutExerciseId}
```

### Exercise Set Management

#### Get exercise sets

```
GET /api/workouts/exercises/{workoutExerciseId}/sets
```

#### Add set to exercise

```
POST /api/workouts/exercises/{workoutExerciseId}/sets
```

Request body:
```json
{
  "reps": 12,
  "weight": 50.0,
  "durationSeconds": null,
  "distance": null,
  "completed": true,
  "order": 1
}
```

#### Update exercise set

```
PUT /api/workouts/sets/{setId}
```

Request body:
```json
{
  "reps": 15,
  "weight": 55.0,
  "completed": true,
  "order": 1
}
```

#### Delete exercise set

```
DELETE /api/workouts/sets/{setId}
```

## Getting Started

### Prerequisites

- JDK 17 or higher
- Docker (for PostgreSQL)

### Running the Application

1. Clone the repository
2. Start the PostgreSQL database:
   ```
   docker-compose up -d
   ```
3. Run the application:
   ```
   ./gradlew bootRun
   ```
4. The API will be available at http://localhost:8080

## Development

### Building the Application

```
./gradlew build
```

### Running Tests

```
./gradlew test
```
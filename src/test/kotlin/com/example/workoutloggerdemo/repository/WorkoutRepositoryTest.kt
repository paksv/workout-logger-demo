package com.example.workoutloggerdemo.repository

import com.example.workoutloggerdemo.model.Workout
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DataJdbcTest
@ActiveProfiles("test")
@EnableJdbcRepositories(basePackages = ["com.example.workoutloggerdemo.repository"])
@TestPropertySource(properties = [
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=password",
    "spring.sql.init.mode=embedded"
])
class WorkoutRepositoryTest {

    @Autowired
    private lateinit var workoutRepository: WorkoutRepository

    @Test
    fun `should save and retrieve workout from database`() {
        // Arrange
        val workout = Workout(
            name = "Morning Run",
            description = "5km run in the park",
            duration = 30,
            caloriesBurned = 300,
            date = LocalDateTime.now()
        )

        // Act
        val savedWorkout = workoutRepository.save(workout)
        val retrievedWorkout = workoutRepository.findById(savedWorkout.id!!).orElse(null)

        // Assert
        assertNotNull(retrievedWorkout)
        assertEquals(workout.name, retrievedWorkout.name)
        assertEquals(workout.description, retrievedWorkout.description)
        assertEquals(workout.duration, retrievedWorkout.duration)
        assertEquals(workout.caloriesBurned, retrievedWorkout.caloriesBurned)
        
        println("[DEBUG_LOG] Saved workout: $savedWorkout")
        println("[DEBUG_LOG] Retrieved workout: $retrievedWorkout")
    }
}
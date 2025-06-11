package com.example.workoutloggerdemo

import com.example.workoutloggerdemo.model.Workout
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
@ActiveProfiles("test")
class WorkoutDatabaseTest {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    
    @BeforeEach
    fun setup() {
        // Create workouts table
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS workouts (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                description VARCHAR(1000),
                duration INT NOT NULL,
                calories_burned INT,
                date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """)
        
        // Clear any existing data
        jdbcTemplate.execute("DELETE FROM workouts")
    }

    @Test
    fun `should insert and retrieve workout from database`() {
        // Arrange
        val workout = Workout(
            name = "Morning Run",
            description = "5km run in the park",
            duration = 30,
            caloriesBurned = 300,
            date = LocalDateTime.now()
        )
        
        // Act - Insert workout using SimpleJdbcInsert
        val simpleJdbcInsert = SimpleJdbcInsert(jdbcTemplate)
            .withTableName("workouts")
            .usingGeneratedKeyColumns("id")
        
        val parameters = mapOf(
            "name" to workout.name,
            "description" to workout.description,
            "duration" to workout.duration,
            "calories_burned" to workout.caloriesBurned,
            "date" to workout.date
        )
        
        val workoutId = simpleJdbcInsert.executeAndReturnKey(parameters).toLong()
        
        // Query the workout
        val retrievedWorkout = jdbcTemplate.queryForMap(
            "SELECT * FROM workouts WHERE id = ?", 
            workoutId
        )
        
        // Assert
        assertNotNull(retrievedWorkout)
        assertEquals(workout.name, retrievedWorkout["name"])
        assertEquals(workout.description, retrievedWorkout["description"])
        assertEquals(workout.duration, retrievedWorkout["duration"])
        assertEquals(workout.caloriesBurned, retrievedWorkout["calories_burned"])
        
        println("[DEBUG_LOG] Inserted workout with ID: $workoutId")
        println("[DEBUG_LOG] Retrieved workout: $retrievedWorkout")
    }
}
package com.example.workoutloggerdemo

import com.example.workoutloggerdemo.model.Workout
import com.example.workoutloggerdemo.repository.WorkoutRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test")
class WorkoutLoggerDemoApplicationTests {

    @Autowired
    private lateinit var workoutRepository: WorkoutRepository

    @Test
    fun contextLoads() {
    }

    @Test
    fun testWorkoutDatabaseOperations() {
        // Create a new workout
        val workout = Workout(
            name = "Morning Run",
            description = "5km run in the park",
            startTime = LocalDateTime.now().minusHours(1),
            endTime = LocalDateTime.now(),
            caloriesBurned = 350
        )

        // Save the workout to the database
        val savedWorkout = workoutRepository.save(workout)

        // Verify the workout was saved with an ID
        assertNotNull(savedWorkout.id)

        // Retrieve the workout from the database
        val retrievedWorkout = workoutRepository.findById(savedWorkout.id!!).orElse(null)

        // Verify the retrieved workout matches the saved one
        assertNotNull(retrievedWorkout)
        assertEquals(workout.name, retrievedWorkout.name)
        assertEquals(workout.description, retrievedWorkout.description)
        assertEquals(workout.caloriesBurned, retrievedWorkout.caloriesBurned)
    }
    
    @Test
    fun testFindAllWorkouts() {
        // Clear any existing data
        workoutRepository.deleteAll()
        
        // Create multiple workouts
        val workout1 = Workout(
            name = "Morning Yoga",
            description = "30 minutes of yoga",
            startTime = LocalDateTime.now().minusDays(1),
            endTime = LocalDateTime.now().minusDays(1).plusMinutes(30),
            caloriesBurned = 150
        )
        
        val workout2 = Workout(
            name = "Evening Run",
            description = "3km run around the neighborhood",
            startTime = LocalDateTime.now().minusHours(5),
            endTime = LocalDateTime.now().minusHours(4),
            caloriesBurned = 250
        )
        
        // Save workouts to the database
        workoutRepository.saveAll(listOf(workout1, workout2))
        
        // Retrieve all workouts
        val allWorkouts = workoutRepository.findAll()
        
        // Verify we have exactly 2 workouts
        assertEquals(2, allWorkouts.size)
        
        // Verify the workouts contain the expected data
        val workoutNames = allWorkouts.map { it.name }.toSet()
        assertTrue(workoutNames.contains("Morning Yoga"))
        assertTrue(workoutNames.contains("Evening Run"))
        
        // Verify calorie data is correct
        val totalCalories = allWorkouts.mapNotNull { it.caloriesBurned }.sum()
        assertEquals(400, totalCalories)
    }
}

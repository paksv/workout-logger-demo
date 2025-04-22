package com.example.workoutloggerdemo.controller

import com.example.workoutloggerdemo.model.ExerciseSet
import com.example.workoutloggerdemo.model.Workout
import com.example.workoutloggerdemo.model.WorkoutExercise
import com.example.workoutloggerdemo.service.WorkoutService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.Duration
import java.time.LocalDate

@RestController
@RequestMapping("/api/workouts")
class WorkoutController(private val workoutService: WorkoutService) {
    
    @GetMapping
    fun getAllWorkouts(): ResponseEntity<List<Workout>> {
        return ResponseEntity.ok(workoutService.getAllWorkouts())
    }
    
    @GetMapping("/{id}")
    fun getWorkoutById(@PathVariable id: Long): ResponseEntity<Workout> {
        return try {
            ResponseEntity.ok(workoutService.findById(id))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @GetMapping("/user/{userId}")
    fun getWorkoutsByUserId(@PathVariable userId: Long): ResponseEntity<List<Workout>> {
        return try {
            ResponseEntity.ok(workoutService.findByUserId(userId))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @GetMapping("/user/{userId}/date-range")
    fun getWorkoutsByDateRange(
        @PathVariable userId: Long,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startDate: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endDate: LocalDate
    ): ResponseEntity<List<Workout>> {
        return try {
            ResponseEntity.ok(workoutService.findByUserIdAndDateRange(userId, startDate, endDate))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @GetMapping("/user/{userId}/recent")
    fun getRecentWorkouts(
        @PathVariable userId: Long,
        @RequestParam(defaultValue = "10") limit: Int
    ): ResponseEntity<List<Workout>> {
        return try {
            ResponseEntity.ok(workoutService.findRecentWorkouts(userId, limit))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @GetMapping("/search")
    fun searchWorkouts(
        @RequestParam userId: Long,
        @RequestParam searchTerm: String
    ): ResponseEntity<List<Workout>> {
        return try {
            ResponseEntity.ok(workoutService.searchWorkoutsByName(userId, searchTerm))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @PostMapping
    fun createWorkout(@RequestBody request: CreateWorkoutRequest): ResponseEntity<Workout> {
        return try {
            val workout = workoutService.createWorkout(
                name = request.name,
                userId = request.userId,
                date = request.date,
                duration = Duration.ofSeconds(request.durationSeconds),
                notes = request.notes
            )
            ResponseEntity.status(HttpStatus.CREATED).body(workout)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @PutMapping("/{id}")
    fun updateWorkout(
        @PathVariable id: Long,
        @RequestBody request: UpdateWorkoutRequest
    ): ResponseEntity<Workout> {
        return try {
            val workout = workoutService.updateWorkout(
                id = id,
                name = request.name,
                date = request.date,
                duration = request.durationSeconds?.let { Duration.ofSeconds(it) },
                notes = request.notes
            )
            ResponseEntity.ok(workout)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteWorkout(@PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            workoutService.deleteWorkout(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    // Workout Exercise endpoints
    
    @GetMapping("/{workoutId}/exercises")
    fun getWorkoutExercises(@PathVariable workoutId: Long): ResponseEntity<List<WorkoutExercise>> {
        return try {
            ResponseEntity.ok(workoutService.getWorkoutExercises(workoutId))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @PostMapping("/{workoutId}/exercises")
    fun addExerciseToWorkout(
        @PathVariable workoutId: Long,
        @RequestBody request: AddExerciseRequest
    ): ResponseEntity<WorkoutExercise> {
        return try {
            val workoutExercise = workoutService.addExerciseToWorkout(
                workoutId = workoutId,
                exerciseId = request.exerciseId,
                order = request.order
            )
            ResponseEntity.status(HttpStatus.CREATED).body(workoutExercise)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @PutMapping("/exercises/{workoutExerciseId}")
    fun updateWorkoutExercise(
        @PathVariable workoutExerciseId: Long,
        @RequestBody request: UpdateWorkoutExerciseRequest
    ): ResponseEntity<WorkoutExercise> {
        return try {
            val workoutExercise = workoutService.updateWorkoutExercise(
                id = workoutExerciseId,
                order = request.order
            )
            ResponseEntity.ok(workoutExercise)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @DeleteMapping("/exercises/{workoutExerciseId}")
    fun removeExerciseFromWorkout(@PathVariable workoutExerciseId: Long): ResponseEntity<Unit> {
        return try {
            workoutService.removeExerciseFromWorkout(workoutExerciseId)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    // Exercise Set endpoints
    
    @GetMapping("/exercises/{workoutExerciseId}/sets")
    fun getExerciseSets(@PathVariable workoutExerciseId: Long): ResponseEntity<List<ExerciseSet>> {
        return try {
            ResponseEntity.ok(workoutService.getExerciseSets(workoutExerciseId))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @PostMapping("/exercises/{workoutExerciseId}/sets")
    fun addSetToExercise(
        @PathVariable workoutExerciseId: Long,
        @RequestBody request: AddSetRequest
    ): ResponseEntity<ExerciseSet> {
        return try {
            val exerciseSet = workoutService.addSetToExercise(
                workoutExerciseId = workoutExerciseId,
                reps = request.reps,
                weight = request.weight,
                duration = request.durationSeconds?.let { Duration.ofSeconds(it) },
                distance = request.distance,
                completed = request.completed ?: false,
                order = request.order
            )
            ResponseEntity.status(HttpStatus.CREATED).body(exerciseSet)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @PutMapping("/sets/{setId}")
    fun updateExerciseSet(
        @PathVariable setId: Long,
        @RequestBody request: UpdateSetRequest
    ): ResponseEntity<ExerciseSet> {
        return try {
            val exerciseSet = workoutService.updateExerciseSet(
                id = setId,
                reps = request.reps,
                weight = request.weight,
                duration = request.durationSeconds?.let { Duration.ofSeconds(it) },
                distance = request.distance,
                completed = request.completed,
                order = request.order
            )
            ResponseEntity.ok(exerciseSet)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @DeleteMapping("/sets/{setId}")
    fun deleteExerciseSet(@PathVariable setId: Long): ResponseEntity<Unit> {
        return try {
            workoutService.deleteExerciseSet(setId)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}

data class CreateWorkoutRequest(
    val name: String,
    val userId: Long,
    val date: LocalDate,
    val durationSeconds: Long,
    val notes: String? = null
)

data class UpdateWorkoutRequest(
    val name: String? = null,
    val date: LocalDate? = null,
    val durationSeconds: Long? = null,
    val notes: String? = null
)

data class AddExerciseRequest(
    val exerciseId: Long,
    val order: Int
)

data class UpdateWorkoutExerciseRequest(
    val order: Int? = null
)

data class AddSetRequest(
    val reps: Int? = null,
    val weight: Double? = null,
    val durationSeconds: Long? = null,
    val distance: Double? = null,
    val completed: Boolean? = false,
    val order: Int
)

data class UpdateSetRequest(
    val reps: Int? = null,
    val weight: Double? = null,
    val durationSeconds: Long? = null,
    val distance: Double? = null,
    val completed: Boolean? = null,
    val order: Int? = null
)
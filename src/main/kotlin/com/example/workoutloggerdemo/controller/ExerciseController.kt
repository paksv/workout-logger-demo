package com.example.workoutloggerdemo.controller

import com.example.workoutloggerdemo.model.Exercise
import com.example.workoutloggerdemo.model.ExerciseCategory
import com.example.workoutloggerdemo.model.MuscleGroup
import com.example.workoutloggerdemo.service.ExerciseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/exercises")
class ExerciseController(private val exerciseService: ExerciseService) {
    
    @GetMapping
    fun getAllExercises(): ResponseEntity<List<Exercise>> {
        return ResponseEntity.ok(exerciseService.getAllExercises())
    }
    
    @GetMapping("/{id}")
    fun getExerciseById(@PathVariable id: Long): ResponseEntity<Exercise> {
        return try {
            ResponseEntity.ok(exerciseService.findById(id))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @GetMapping("/category/{category}")
    fun getExercisesByCategory(@PathVariable category: ExerciseCategory): ResponseEntity<List<Exercise>> {
        return ResponseEntity.ok(exerciseService.findByCategory(category))
    }
    
    @GetMapping("/user/{userId}/custom")
    fun getCustomExercisesForUser(@PathVariable userId: Long): ResponseEntity<List<Exercise>> {
        return try {
            ResponseEntity.ok(exerciseService.findCustomExercisesForUser(userId))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @GetMapping("/user/{userId}/available")
    fun getAvailableExercisesForUser(@PathVariable userId: Long): ResponseEntity<List<Exercise>> {
        return try {
            ResponseEntity.ok(exerciseService.findAllAvailableForUser(userId))
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @GetMapping("/search")
    fun searchExercisesByName(@RequestParam searchTerm: String): ResponseEntity<List<Exercise>> {
        return ResponseEntity.ok(exerciseService.searchByName(searchTerm))
    }
    
    @PostMapping
    fun createExercise(@RequestBody request: CreateExerciseRequest): ResponseEntity<Exercise> {
        return try {
            val exercise = exerciseService.createExercise(
                name = request.name,
                description = request.description,
                category = request.category,
                muscleGroups = request.muscleGroups,
                isCustom = request.isCustom,
                userId = request.userId
            )
            ResponseEntity.status(HttpStatus.CREATED).body(exercise)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @PutMapping("/{id}")
    fun updateExercise(
        @PathVariable id: Long,
        @RequestBody request: UpdateExerciseRequest
    ): ResponseEntity<Exercise> {
        return try {
            val exercise = exerciseService.updateExercise(
                id = id,
                name = request.name,
                description = request.description,
                category = request.category,
                muscleGroups = request.muscleGroups
            )
            ResponseEntity.ok(exercise)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteExercise(@PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            exerciseService.deleteExercise(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
}

data class CreateExerciseRequest(
    val name: String,
    val description: String,
    val category: ExerciseCategory,
    val muscleGroups: List<MuscleGroup>,
    val isCustom: Boolean = false,
    val userId: Long? = null
)

data class UpdateExerciseRequest(
    val name: String? = null,
    val description: String? = null,
    val category: ExerciseCategory? = null,
    val muscleGroups: List<MuscleGroup>? = null
)
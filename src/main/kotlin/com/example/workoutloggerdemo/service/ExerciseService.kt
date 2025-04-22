package com.example.workoutloggerdemo.service

import com.example.workoutloggerdemo.model.Exercise
import com.example.workoutloggerdemo.model.ExerciseCategory
import com.example.workoutloggerdemo.model.MuscleGroup
import com.example.workoutloggerdemo.repository.ExerciseRepository
import com.example.workoutloggerdemo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ExerciseService(
    private val exerciseRepository: ExerciseRepository,
    private val userRepository: UserRepository
) {
    fun findById(id: Long): Exercise {
        return exerciseRepository.findById(id).orElseThrow { NoSuchElementException("Exercise not found with id: $id") }
    }
    
    fun findByCategory(category: ExerciseCategory): List<Exercise> {
        return exerciseRepository.findByCategory(category)
    }
    
    fun findCustomExercisesForUser(userId: Long): List<Exercise> {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("User not found with id: $userId")
        }
        
        return exerciseRepository.findByIsCustomAndUserId(true, userId)
    }
    
    fun findAllAvailableForUser(userId: Long): List<Exercise> {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("User not found with id: $userId")
        }
        
        return exerciseRepository.findAllAvailableForUser(userId)
    }
    
    fun searchByName(searchTerm: String): List<Exercise> {
        return exerciseRepository.searchByName(searchTerm)
    }
    
    fun createExercise(
        name: String,
        description: String,
        category: ExerciseCategory,
        muscleGroups: List<MuscleGroup>,
        isCustom: Boolean = false,
        userId: Long? = null
    ): Exercise {
        if (isCustom && userId == null) {
            throw IllegalArgumentException("User ID is required for custom exercises")
        }
        
        if (userId != null && !userRepository.existsById(userId)) {
            throw IllegalArgumentException("User not found with id: $userId")
        }
        
        val exercise = Exercise(
            name = name,
            description = description,
            category = category,
            muscleGroups = muscleGroups,
            isCustom = isCustom,
            userId = userId
        )
        
        return exerciseRepository.save(exercise)
    }
    
    fun updateExercise(
        id: Long,
        name: String? = null,
        description: String? = null,
        category: ExerciseCategory? = null,
        muscleGroups: List<MuscleGroup>? = null
    ): Exercise {
        val exercise = findById(id)
        
        val updatedExercise = exercise.copy(
            name = name ?: exercise.name,
            description = description ?: exercise.description,
            category = category ?: exercise.category,
            muscleGroups = muscleGroups ?: exercise.muscleGroups
        )
        
        return exerciseRepository.save(updatedExercise)
    }
    
    fun deleteExercise(id: Long) {
        val exercise = findById(id)
        
        if (!exercise.isCustom) {
            throw IllegalArgumentException("Cannot delete predefined exercise")
        }
        
        exerciseRepository.deleteById(id)
    }
    
    fun getAllExercises(): List<Exercise> {
        return exerciseRepository.findAll().toList()
    }
}
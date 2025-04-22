package com.example.workoutloggerdemo.service

import com.example.workoutloggerdemo.model.ExerciseSet
import com.example.workoutloggerdemo.model.Workout
import com.example.workoutloggerdemo.model.WorkoutExercise
import com.example.workoutloggerdemo.repository.ExerciseRepository
import com.example.workoutloggerdemo.repository.ExerciseSetRepository
import com.example.workoutloggerdemo.repository.UserRepository
import com.example.workoutloggerdemo.repository.WorkoutExerciseRepository
import com.example.workoutloggerdemo.repository.WorkoutRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate

@Service
class WorkoutService(
    private val workoutRepository: WorkoutRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository,
    private val exerciseSetRepository: ExerciseSetRepository,
    private val exerciseRepository: ExerciseRepository,
    private val userRepository: UserRepository
) {
    fun findById(id: Long): Workout {
        return workoutRepository.findById(id).orElseThrow { NoSuchElementException("Workout not found with id: $id") }
    }
    
    fun findByUserId(userId: Long): List<Workout> {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("User not found with id: $userId")
        }
        
        return workoutRepository.findByUserId(userId)
    }
    
    fun findByUserIdAndDateRange(userId: Long, startDate: LocalDate, endDate: LocalDate): List<Workout> {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("User not found with id: $userId")
        }
        
        return workoutRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
    }
    
    fun findRecentWorkouts(userId: Long, limit: Int = 10): List<Workout> {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("User not found with id: $userId")
        }
        
        return workoutRepository.findRecentWorkoutsByUserId(userId, limit)
    }
    
    fun searchWorkoutsByName(userId: Long, searchTerm: String): List<Workout> {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("User not found with id: $userId")
        }
        
        return workoutRepository.searchByNameForUser(userId, searchTerm)
    }
    
    @Transactional
    fun createWorkout(
        name: String,
        userId: Long,
        date: LocalDate,
        duration: Duration,
        notes: String? = null
    ): Workout {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("User not found with id: $userId")
        }
        
        val workout = Workout(
            name = name,
            userId = userId,
            date = date,
            duration = duration,
            notes = notes
        )
        
        return workoutRepository.save(workout)
    }
    
    @Transactional
    fun updateWorkout(
        id: Long,
        name: String? = null,
        date: LocalDate? = null,
        duration: Duration? = null,
        notes: String? = null
    ): Workout {
        val workout = findById(id)
        
        val updatedWorkout = workout.copy(
            name = name ?: workout.name,
            date = date ?: workout.date,
            duration = duration ?: workout.duration,
            notes = notes ?: workout.notes
        )
        
        return workoutRepository.save(updatedWorkout)
    }
    
    @Transactional
    fun deleteWorkout(id: Long) {
        if (!workoutRepository.existsById(id)) {
            throw NoSuchElementException("Workout not found with id: $id")
        }
        
        // Cascade delete will handle workout exercises and exercise sets
        workoutRepository.deleteById(id)
    }
    
    fun getAllWorkouts(): List<Workout> {
        return workoutRepository.findAll().toList()
    }
    
    // Workout Exercise methods
    
    fun getWorkoutExercises(workoutId: Long): List<WorkoutExercise> {
        if (!workoutRepository.existsById(workoutId)) {
            throw NoSuchElementException("Workout not found with id: $workoutId")
        }
        
        return workoutExerciseRepository.findByWorkoutIdOrderByOrder(workoutId)
    }
    
    @Transactional
    fun addExerciseToWorkout(workoutId: Long, exerciseId: Long, order: Int): WorkoutExercise {
        if (!workoutRepository.existsById(workoutId)) {
            throw NoSuchElementException("Workout not found with id: $workoutId")
        }
        
        if (!exerciseRepository.existsById(exerciseId)) {
            throw NoSuchElementException("Exercise not found with id: $exerciseId")
        }
        
        val workoutExercise = WorkoutExercise(
            workoutId = workoutId,
            exerciseId = exerciseId,
            order = order
        )
        
        return workoutExerciseRepository.save(workoutExercise)
    }
    
    @Transactional
    fun updateWorkoutExercise(id: Long, order: Int? = null): WorkoutExercise {
        val workoutExercise = workoutExerciseRepository.findById(id)
            .orElseThrow { NoSuchElementException("Workout exercise not found with id: $id") }
        
        val updatedWorkoutExercise = workoutExercise.copy(
            order = order ?: workoutExercise.order
        )
        
        return workoutExerciseRepository.save(updatedWorkoutExercise)
    }
    
    @Transactional
    fun removeExerciseFromWorkout(workoutExerciseId: Long) {
        if (!workoutExerciseRepository.existsById(workoutExerciseId)) {
            throw NoSuchElementException("Workout exercise not found with id: $workoutExerciseId")
        }
        
        // Cascade delete will handle exercise sets
        workoutExerciseRepository.deleteById(workoutExerciseId)
    }
    
    // Exercise Set methods
    
    fun getExerciseSets(workoutExerciseId: Long): List<ExerciseSet> {
        if (!workoutExerciseRepository.existsById(workoutExerciseId)) {
            throw NoSuchElementException("Workout exercise not found with id: $workoutExerciseId")
        }
        
        return exerciseSetRepository.findByWorkoutExerciseIdOrderByOrder(workoutExerciseId)
    }
    
    @Transactional
    fun addSetToExercise(
        workoutExerciseId: Long,
        reps: Int? = null,
        weight: Double? = null,
        duration: Duration? = null,
        distance: Double? = null,
        completed: Boolean = false,
        order: Int
    ): ExerciseSet {
        if (!workoutExerciseRepository.existsById(workoutExerciseId)) {
            throw NoSuchElementException("Workout exercise not found with id: $workoutExerciseId")
        }
        
        val exerciseSet = ExerciseSet(
            workoutExerciseId = workoutExerciseId,
            reps = reps,
            weight = weight,
            duration = duration,
            distance = distance,
            completed = completed,
            order = order
        )
        
        return exerciseSetRepository.save(exerciseSet)
    }
    
    @Transactional
    fun updateExerciseSet(
        id: Long,
        reps: Int? = null,
        weight: Double? = null,
        duration: Duration? = null,
        distance: Double? = null,
        completed: Boolean? = null,
        order: Int? = null
    ): ExerciseSet {
        val exerciseSet = exerciseSetRepository.findById(id)
            .orElseThrow { NoSuchElementException("Exercise set not found with id: $id") }
        
        val updatedExerciseSet = exerciseSet.copy(
            reps = reps ?: exerciseSet.reps,
            weight = weight ?: exerciseSet.weight,
            duration = duration ?: exerciseSet.duration,
            distance = distance ?: exerciseSet.distance,
            completed = completed ?: exerciseSet.completed,
            order = order ?: exerciseSet.order
        )
        
        return exerciseSetRepository.save(updatedExerciseSet)
    }
    
    @Transactional
    fun deleteExerciseSet(id: Long) {
        if (!exerciseSetRepository.existsById(id)) {
            throw NoSuchElementException("Exercise set not found with id: $id")
        }
        
        exerciseSetRepository.deleteById(id)
    }
}
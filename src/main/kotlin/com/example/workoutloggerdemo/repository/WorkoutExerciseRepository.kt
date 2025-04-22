package com.example.workoutloggerdemo.repository

import com.example.workoutloggerdemo.model.WorkoutExercise
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface WorkoutExerciseRepository : CrudRepository<WorkoutExercise, Long> {
    fun findByWorkoutId(workoutId: Long): List<WorkoutExercise>
    
    fun findByWorkoutIdOrderByOrder(workoutId: Long): List<WorkoutExercise>
    
    @Query("DELETE FROM workout_exercises WHERE workout_id = :workoutId")
    fun deleteByWorkoutId(@Param("workoutId") workoutId: Long)
    
    @Query("SELECT we.* FROM workout_exercises we JOIN workouts w ON we.workout_id = w.id WHERE w.user_id = :userId")
    fun findByUserId(@Param("userId") userId: Long): List<WorkoutExercise>
}
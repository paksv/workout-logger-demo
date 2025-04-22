package com.example.workoutloggerdemo.repository

import com.example.workoutloggerdemo.model.ExerciseSet
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ExerciseSetRepository : CrudRepository<ExerciseSet, Long> {
    fun findByWorkoutExerciseId(workoutExerciseId: Long): List<ExerciseSet>
    
    fun findByWorkoutExerciseIdOrderByOrder(workoutExerciseId: Long): List<ExerciseSet>
    
    @Query("DELETE FROM exercise_sets WHERE workout_exercise_id = :workoutExerciseId")
    fun deleteByWorkoutExerciseId(@Param("workoutExerciseId") workoutExerciseId: Long)
    
    @Query("""
        SELECT es.* FROM exercise_sets es 
        JOIN workout_exercises we ON es.workout_exercise_id = we.id 
        JOIN workouts w ON we.workout_id = w.id 
        WHERE w.user_id = :userId
    """)
    fun findByUserId(@Param("userId") userId: Long): List<ExerciseSet>
    
    @Query("""
        SELECT es.* FROM exercise_sets es 
        JOIN workout_exercises we ON es.workout_exercise_id = we.id 
        WHERE we.workout_id = :workoutId
    """)
    fun findByWorkoutId(@Param("workoutId") workoutId: Long): List<ExerciseSet>
}
package com.example.workoutloggerdemo.repository

import com.example.workoutloggerdemo.model.Workout
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface WorkoutRepository : CrudRepository<Workout, Long> {
    fun findByUserId(userId: Long): List<Workout>
    
    fun findByUserIdAndDateBetween(userId: Long, startDate: LocalDate, endDate: LocalDate): List<Workout>
    
    @Query("SELECT * FROM workouts WHERE user_id = :userId ORDER BY date DESC LIMIT :limit")
    fun findRecentWorkoutsByUserId(@Param("userId") userId: Long, @Param("limit") limit: Int): List<Workout>
    
    @Query("SELECT * FROM workouts WHERE user_id = :userId AND name ILIKE CONCAT('%', :searchTerm, '%')")
    fun searchByNameForUser(@Param("userId") userId: Long, @Param("searchTerm") searchTerm: String): List<Workout>
}
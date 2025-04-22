package com.example.workoutloggerdemo.repository

import com.example.workoutloggerdemo.model.Exercise
import com.example.workoutloggerdemo.model.ExerciseCategory
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ExerciseRepository : CrudRepository<Exercise, Long> {
    fun findByCategory(category: ExerciseCategory): List<Exercise>
    
    fun findByIsCustomAndUserId(isCustom: Boolean, userId: Long?): List<Exercise>
    
    @Query("SELECT * FROM exercises WHERE is_custom = false OR user_id = :userId")
    fun findAllAvailableForUser(@Param("userId") userId: Long): List<Exercise>
    
    @Query("SELECT * FROM exercises WHERE name ILIKE CONCAT('%', :searchTerm, '%')")
    fun searchByName(@Param("searchTerm") searchTerm: String): List<Exercise>
}
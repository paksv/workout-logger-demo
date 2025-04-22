package com.example.workoutloggerdemo.repository

import com.example.workoutloggerdemo.model.Athlete
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AthleteRepository : CrudRepository<Athlete, Long> {
    fun findByUserId(userId: Long): Optional<Athlete>
    
    @Query("SELECT EXISTS(SELECT 1 FROM athletes WHERE user_id = :userId)")
    fun existsByUserId(@Param("userId") userId: Long): Boolean
}
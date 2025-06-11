package com.example.workoutloggerdemo.repository

import com.example.workoutloggerdemo.model.Workout
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutRepository : CrudRepository<Workout, Long> {
    fun findByName(name: String): List<Workout>
}
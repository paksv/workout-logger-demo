package com.example.workoutloggerdemo.repository

import com.example.workoutloggerdemo.model.Workout
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutRepository : JpaRepository<Workout, Long>
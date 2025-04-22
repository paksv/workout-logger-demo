package com.example.workoutloggerdemo.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("workout_exercises")
data class WorkoutExercise(
    @Id
    val id: Long? = null,
    val workoutId: Long,
    val exerciseId: Long,
    val order: Int
)
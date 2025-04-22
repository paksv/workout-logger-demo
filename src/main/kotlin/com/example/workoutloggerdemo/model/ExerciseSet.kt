package com.example.workoutloggerdemo.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Duration

@Table("exercise_sets")
data class ExerciseSet(
    @Id
    val id: Long? = null,
    val workoutExerciseId: Long,
    val reps: Int? = null,
    val weight: Double? = null,
    val duration: Duration? = null,
    val distance: Double? = null,
    val completed: Boolean = false,
    val order: Int
)
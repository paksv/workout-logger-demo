package com.example.workoutloggerdemo.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

enum class ExerciseCategory {
    STRENGTH,
    CARDIO,
    FLEXIBILITY,
    BALANCE
}

enum class MuscleGroup {
    CHEST,
    BACK,
    SHOULDERS,
    BICEPS,
    TRICEPS,
    LEGS,
    CORE,
    FULL_BODY
}

@Table("exercises")
data class Exercise(
    @Id
    val id: Long? = null,
    val name: String,
    val description: String,
    val category: ExerciseCategory,
    val muscleGroups: List<MuscleGroup>,
    val isCustom: Boolean = false,
    val userId: Long? = null, // Null for predefined exercises
    val createdAt: LocalDateTime = LocalDateTime.now()
)

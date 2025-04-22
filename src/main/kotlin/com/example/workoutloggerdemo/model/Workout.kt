package com.example.workoutloggerdemo.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@Table("workouts")
data class Workout(
    @Id
    val id: Long? = null,
    val name: String,
    val userId: Long,
    val date: LocalDate,
    val duration: Duration,
    val notes: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

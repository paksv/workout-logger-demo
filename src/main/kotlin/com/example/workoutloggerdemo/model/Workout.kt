package com.example.workoutloggerdemo.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("workouts")
data class Workout(
    @Id
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val duration: Int, // in minutes
    @Column("calories_burned")
    val caloriesBurned: Int? = null,
    val date: LocalDateTime = LocalDateTime.now()
)
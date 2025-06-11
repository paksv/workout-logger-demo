package com.example.workoutloggerdemo.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "workouts")
data class Workout(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    val name: String = "",
    
    @Column
    val description: String? = null,
    
    @Column(name = "start_time", nullable = false)
    val startTime: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "end_time")
    val endTime: LocalDateTime? = null,
    
    @Column(name = "calories_burned")
    val caloriesBurned: Int? = null
) {
    // No-args constructor required by JPA
    constructor() : this(
        name = "",
        startTime = LocalDateTime.now()
    )
}
package com.example.workoutloggerdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkoutLoggerDemoApplication

fun main(args: Array<String>) {
    runApplication<WorkoutLoggerDemoApplication>(*args)
}

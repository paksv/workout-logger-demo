package com.example.workoutloggerdemo.controller

import com.example.workoutloggerdemo.model.Athlete
import com.example.workoutloggerdemo.service.AthleteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/athletes")
class AthleteController(private val athleteService: AthleteService) {
    
    @GetMapping
    fun getAllAthletes(): ResponseEntity<List<Athlete>> {
        return ResponseEntity.ok(athleteService.getAllAthletes())
    }
    
    @GetMapping("/{id}")
    fun getAthleteById(@PathVariable id: Long): ResponseEntity<Athlete> {
        return try {
            ResponseEntity.ok(athleteService.findById(id))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @GetMapping("/user/{userId}")
    fun getAthleteByUserId(@PathVariable userId: Long): ResponseEntity<Athlete> {
        return try {
            ResponseEntity.ok(athleteService.findByUserId(userId))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @PostMapping
    fun createAthlete(@RequestBody request: CreateAthleteRequest): ResponseEntity<Athlete> {
        return try {
            val athlete = athleteService.createAthlete(
                userId = request.userId,
                height = request.height,
                weight = request.weight,
                fitnessGoals = request.fitnessGoals
            )
            ResponseEntity.status(HttpStatus.CREATED).body(athlete)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @PutMapping("/{id}")
    fun updateAthlete(
        @PathVariable id: Long,
        @RequestBody request: UpdateAthleteRequest
    ): ResponseEntity<Athlete> {
        return try {
            val athlete = athleteService.updateAthlete(
                id = id,
                height = request.height,
                weight = request.weight,
                fitnessGoals = request.fitnessGoals
            )
            ResponseEntity.ok(athlete)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @PutMapping("/user/{userId}")
    fun updateAthleteByUserId(
        @PathVariable userId: Long,
        @RequestBody request: UpdateAthleteRequest
    ): ResponseEntity<Athlete> {
        return try {
            val athlete = athleteService.updateAthleteByUserId(
                userId = userId,
                height = request.height,
                weight = request.weight,
                fitnessGoals = request.fitnessGoals
            )
            ResponseEntity.ok(athlete)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteAthlete(@PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            athleteService.deleteAthlete(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}

data class CreateAthleteRequest(
    val userId: Long,
    val height: Double? = null,
    val weight: Double? = null,
    val fitnessGoals: String? = null
)

data class UpdateAthleteRequest(
    val height: Double? = null,
    val weight: Double? = null,
    val fitnessGoals: String? = null
)
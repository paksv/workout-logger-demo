package com.example.workoutloggerdemo.service

import com.example.workoutloggerdemo.model.Athlete
import com.example.workoutloggerdemo.repository.AthleteRepository
import com.example.workoutloggerdemo.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AthleteService(
    private val athleteRepository: AthleteRepository,
    private val userRepository: UserRepository
) {
    fun findById(id: Long): Athlete {
        return athleteRepository.findById(id).orElseThrow { NoSuchElementException("Athlete not found with id: $id") }
    }
    
    fun findByUserId(userId: Long): Athlete {
        return athleteRepository.findByUserId(userId).orElseThrow { NoSuchElementException("Athlete not found for user id: $userId") }
    }
    
    fun createAthlete(userId: Long, height: Double? = null, weight: Double? = null, fitnessGoals: String? = null): Athlete {
        if (!userRepository.existsById(userId)) {
            throw IllegalArgumentException("User not found with id: $userId")
        }
        
        if (athleteRepository.existsByUserId(userId)) {
            throw IllegalArgumentException("Athlete already exists for user id: $userId")
        }
        
        val athlete = Athlete(
            userId = userId,
            height = height,
            weight = weight,
            fitnessGoals = fitnessGoals
        )
        
        return athleteRepository.save(athlete)
    }
    
    fun updateAthlete(id: Long, height: Double? = null, weight: Double? = null, fitnessGoals: String? = null): Athlete {
        val athlete = findById(id)
        
        val updatedAthlete = athlete.copy(
            height = height ?: athlete.height,
            weight = weight ?: athlete.weight,
            fitnessGoals = fitnessGoals ?: athlete.fitnessGoals,
            updatedAt = LocalDateTime.now()
        )
        
        return athleteRepository.save(updatedAthlete)
    }
    
    fun updateAthleteByUserId(userId: Long, height: Double? = null, weight: Double? = null, fitnessGoals: String? = null): Athlete {
        val athlete = findByUserId(userId)
        
        val updatedAthlete = athlete.copy(
            height = height ?: athlete.height,
            weight = weight ?: athlete.weight,
            fitnessGoals = fitnessGoals ?: athlete.fitnessGoals,
            updatedAt = LocalDateTime.now()
        )
        
        return athleteRepository.save(updatedAthlete)
    }
    
    fun deleteAthlete(id: Long) {
        if (!athleteRepository.existsById(id)) {
            throw NoSuchElementException("Athlete not found with id: $id")
        }
        
        athleteRepository.deleteById(id)
    }
    
    fun getAllAthletes(): List<Athlete> {
        return athleteRepository.findAll().toList()
    }
}
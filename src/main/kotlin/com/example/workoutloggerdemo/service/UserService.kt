package com.example.workoutloggerdemo.service

import com.example.workoutloggerdemo.model.User
import com.example.workoutloggerdemo.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun findById(id: Long): User {
        return userRepository.findById(id).orElseThrow { NoSuchElementException("User not found with id: $id") }
    }
    
    fun findByUsername(username: String): User {
        return userRepository.findByUsername(username).orElseThrow { NoSuchElementException("User not found with username: $username") }
    }
    
    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email).orElseThrow { NoSuchElementException("User not found with email: $email") }
    }
    
    fun createUser(username: String, email: String, password: String): User {
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("Username already exists")
        }
        
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("Email already exists")
        }
        
        val user = User(
            username = username,
            email = email,
            passwordHash = passwordEncoder.encode(password)
        )
        
        return userRepository.save(user)
    }
    
    fun updateUser(id: Long, username: String? = null, email: String? = null, password: String? = null): User {
        val user = findById(id)
        
        val updatedUser = user.copy(
            username = username ?: user.username,
            email = email ?: user.email,
            passwordHash = if (password != null) passwordEncoder.encode(password) else user.passwordHash,
            updatedAt = LocalDateTime.now()
        )
        
        return userRepository.save(updatedUser)
    }
    
    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("User not found with id: $id")
        }
        
        userRepository.deleteById(id)
    }
    
    fun getAllUsers(): List<User> {
        return userRepository.findAll().toList()
    }
}
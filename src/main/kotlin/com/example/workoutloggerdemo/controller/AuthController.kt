package com.example.workoutloggerdemo.controller

import com.example.workoutloggerdemo.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {
    
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        try {
            val user = userService.findByUsername(request.username)
            
            if (!passwordEncoder.matches(request.password, user.passwordHash)) {
                throw BadCredentialsException("Invalid password")
            }
            
            // In a real application, we would generate and return a JWT token here
            // For simplicity, we'll just return a success message with the user ID
            
            return ResponseEntity.ok(LoginResponse(
                userId = user.id!!,
                username = user.username,
                message = "Login successful"
            ))
            
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password")
        } catch (e: BadCredentialsException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password")
        }
    }
}

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val userId: Long,
    val username: String,
    val message: String
)
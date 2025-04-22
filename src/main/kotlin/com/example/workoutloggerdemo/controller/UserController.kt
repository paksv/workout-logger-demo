package com.example.workoutloggerdemo.controller

import com.example.workoutloggerdemo.model.User
import com.example.workoutloggerdemo.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }
    
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        return try {
            ResponseEntity.ok(userService.findById(id))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    
    @PostMapping("/register")
    fun registerUser(@RequestBody request: RegisterUserRequest): ResponseEntity<User> {
        return try {
            val user = userService.createUser(
                username = request.username,
                email = request.email,
                password = request.password
            )
            ResponseEntity.status(HttpStatus.CREATED).body(user)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<User> {
        return try {
            val user = userService.updateUser(
                id = id,
                username = request.username,
                email = request.email,
                password = request.password
            )
            ResponseEntity.ok(user)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }
    
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            userService.deleteUser(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}

data class RegisterUserRequest(
    val username: String,
    val email: String,
    val password: String
)

data class UpdateUserRequest(
    val username: String? = null,
    val email: String? = null,
    val password: String? = null
)
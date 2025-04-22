package com.example.workoutloggerdemo.repository

import com.example.workoutloggerdemo.model.User
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    
    fun findByEmail(email: String): Optional<User>
    
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE username = :username)")
    fun existsByUsername(@Param("username") username: String): Boolean
    
    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)")
    fun existsByEmail(@Param("email") email: String): Boolean
}
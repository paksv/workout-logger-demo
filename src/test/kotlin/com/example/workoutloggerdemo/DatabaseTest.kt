package com.example.workoutloggerdemo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
@ActiveProfiles("test")
class DatabaseTest {

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @Test
    fun `should connect to database and execute query`() {
        // Create a test table
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS test_table (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL
            )
        """)

        // Insert test data
        jdbcTemplate.update("INSERT INTO test_table (name) VALUES (?)", "Test Name")

        // Query the data
        val count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM test_table", Int::class.java)
        val name = jdbcTemplate.queryForObject("SELECT name FROM test_table WHERE id = 1", String::class.java)

        // Assert
        assertEquals(1, count)
        assertEquals("Test Name", name)
        
        println("[DEBUG_LOG] Database test successful. Count: $count, Name: $name")
    }
}
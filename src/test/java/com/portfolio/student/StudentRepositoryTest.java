package com.portfolio.student;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {
    @Test
    void createListDeleteAndHandleDuplicateAndMissingRecords() throws Exception {
        StudentRepository repository =
                new StudentRepository("jdbc:h2:mem:students_crud;DB_CLOSE_DELAY=-1");

        repository.initialize();
        repository.initialize(); // Schema initialization must be safe to repeat.

        long firstId = repository.add("Asha", "asha@example.test", "Computer Science");
        long secondId = repository.add("Ravi", "ravi@example.test", "Information Technology");

        List<String> rows = repository.findAll();
        assertEquals(2, rows.size());
        assertTrue(rows.get(0).contains("Asha"));
        assertTrue(rows.get(1).contains("Ravi"));

        assertThrows(SQLException.class,
                () -> repository.add("Duplicate", "asha@example.test", "Other"));

        // Values containing SQL syntax must remain data because inserts use PreparedStatement.
        long specialId = repository.add(
                "Asha'); DROP TABLE students; --",
                "safe@example.test",
                "Computer Science");
        assertTrue(repository.findAll().stream()
                .anyMatch(row -> row.contains("Asha'); DROP TABLE students; --")));

        assertTrue(repository.delete(firstId));
        assertFalse(repository.delete(firstId));
        assertTrue(repository.delete(secondId));
        assertTrue(repository.delete(specialId));
        assertTrue(repository.findAll().isEmpty());
    }
}

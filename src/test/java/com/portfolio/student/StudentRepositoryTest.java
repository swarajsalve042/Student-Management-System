package com.portfolio.student;

import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {
    private StudentRepository newRepository(String database) throws SQLException {
        StudentRepository repository = new StudentRepository("jdbc:h2:mem:" + database + ";DB_CLOSE_DELAY=-1");
        repository.initialize();
        return repository;
    }

    @Test
    void createsListsFindsUpdatesAndDeletesStudents() throws Exception {
        StudentRepository repository = newRepository("students_crud_v2");

        long firstId = repository.add("  Asha  ", "ASHA@example.test", " Computer Science ");
        long secondId = repository.add("Ravi", "ravi@example.test", "Information Technology");

        List<String> rows = repository.findAll();
        assertEquals(2, rows.size());
        assertTrue(rows.get(0).contains("Asha|asha@example.test|Computer Science"));
        assertTrue(rows.get(1).contains("Ravi"));

        assertTrue(repository.findById(firstId).orElseThrow().contains("Asha|asha@example.test"));
        assertTrue(repository.findById(99999).isEmpty());

        assertTrue(repository.update(firstId, "Asha Patil", "asha.patil@example.test", "Cloud Computing"));
        assertTrue(repository.findById(firstId).orElseThrow()
                .contains("Asha Patil|asha.patil@example.test|Cloud Computing"));
        assertFalse(repository.update(99999, "Missing", "missing@example.test", "Other"));

        assertThrows(SQLException.class,
                () -> repository.add("Duplicate", "asha.patil@example.test", "Other"));

        // SQL-looking input is stored as data because all writes use PreparedStatement.
        long specialId = repository.add(
                "Asha'); DROP TABLE students; --", "safe@example.test", "Computer Science");
        assertTrue(repository.findById(specialId).orElseThrow()
                .contains("Asha'); DROP TABLE students; --"));

        assertTrue(repository.delete(firstId));
        assertFalse(repository.delete(firstId));
        assertTrue(repository.delete(secondId));
        assertTrue(repository.delete(specialId));
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void validatesStudentFieldsAndIdentifiers() throws Exception {
        StudentRepository repository = newRepository("students_validation");

        assertThrows(IllegalArgumentException.class,
                () -> repository.add(" ", "valid@example.test", "Computer Science"));
        assertThrows(IllegalArgumentException.class,
                () -> repository.add("Valid", "not-an-email", "Computer Science"));
        assertThrows(IllegalArgumentException.class,
                () -> repository.add("Valid", "valid@example.test", " "));
        assertThrows(IllegalArgumentException.class,
                () -> repository.add("x".repeat(121), "valid@example.test", "Computer Science"));
        assertThrows(IllegalArgumentException.class,
                () -> repository.findById(0));
        assertThrows(IllegalArgumentException.class,
                () -> repository.delete(-1));
        assertThrows(IllegalArgumentException.class,
                () -> repository.update(0, "Valid", "valid@example.test", "Course"));
    }

    @Test
    void initializesSchemaRepeatedlyAndRejectsInvalidJdbcUrl() throws Exception {
        StudentRepository repository = new StudentRepository("jdbc:h2:mem:students_init;DB_CLOSE_DELAY=-1");
        repository.initialize();
        repository.initialize();
        assertThrows(IllegalArgumentException.class, () -> new StudentRepository("not-a-jdbc-url"));
    }
}

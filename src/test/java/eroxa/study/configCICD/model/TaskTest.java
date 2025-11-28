package eroxa.study.configCICD.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskCreation() {
        // Given
        Integer id = 1;
        String title = "Test Task";
        boolean completed = false;
        LocalDate createdAt = LocalDate.now();

        // When
        Task task = new Task(id, title, completed, createdAt);

        // Then
        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(completed, task.isCompleted());
        assertEquals(createdAt, task.getCreatedAT());
    }

    @Test
    void testTaskEqualsAndHashCode() {
        // Given
        LocalDate now = LocalDate.now();
        Task task1 = new Task(1, "Task 1", false, now);
        Task task2 = new Task(1, "Task 1", false, now);
        Task task3 = new Task(2, "Task 2", true, now);

        // Then
        assertEquals(task1, task2);
        assertNotEquals(task1, task3);
        assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    void testTaskToString() {
        // Given
        Task task = new Task(1, "Test Task", false, LocalDate.of(2023, 12, 1));

        // When
        String toString = task.toString();

        // Then
        assertTrue(toString.contains("Test Task"));
        assertTrue(toString.contains("id=1"));
    }

    @Test
    void testTaskSetters() {
        // Given
        Task task = new Task(1, "Test Task", false, LocalDate.now());

        // When
        task.setCompleted(true);

        // Then
        assertTrue(task.isCompleted());
    }
}
package eroxa.study.configCICD.repository;

import eroxa.study.configCICD.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskRepositoryTest {

    private InMemoryTaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();
    }

    @Test
    void testRemoveById_Existing() {
        // Given
        Task task = new Task(1, "Test Task", false, LocalDate.now());
        repository.save(task);

        // When
        boolean result = repository.removeById(1);

        // Then
        assertTrue(result); // Должен вернуть true при успешном удалении
    }

    @Test
    void testRemoveById_NonExistent() {
        // When
        boolean result = repository.removeById(999); // Несуществующий ID

        // Then - ДОЛЖЕН ВЕРНУТЬ false, так как элемента не было
        assertFalse(result); // Исправлено: ожидаем false, а не true
    }

    @Test
    void testGetById_Existing() {
        // Given
        Task expected = new Task(1, "Test Task", false, LocalDate.now());
        repository.save(expected);

        // When
        Task result = repository.getById(1);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void testGetById_NonExistent() {
        // When & Then
        assertThrows(ResponseStatusException.class, () -> repository.getById(999));
    }

    @Test
    void testAll() {
        // Given
        Task task1 = new Task(1, "Task 1", false, LocalDate.now());
        Task task2 = new Task(2, "Task 2", true, LocalDate.now());
        repository.save(task1);
        repository.save(task2);

        // When
        List<Task> result = repository.all();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains(task1));
        assertTrue(result.contains(task2));
    }

    @Test
    void testSave() {
        // Given
        Task task = new Task(1, "New Task", false, LocalDate.now());

        // When
        repository.save(task);

        // Then
        Task saved = repository.getById(1);
        assertEquals(task, saved);
    }
}
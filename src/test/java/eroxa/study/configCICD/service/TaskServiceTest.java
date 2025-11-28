package eroxa.study.configCICD.service;

import eroxa.study.configCICD.model.Task;
import eroxa.study.configCICD.model.dto.CreateTaskRequest;
import eroxa.study.configCICD.repository.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private InMemoryTaskRepository repository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();
        taskService = new TaskService(repository);
    }

    @Test
    void testGetAllTasks() {
        // Подготовка
        Task task1 = taskService.createTask(new CreateTaskRequest("Task 1"));
        Task task2 = taskService.createTask(new CreateTaskRequest("Task 2"));

        // Действие
        List<Task> result = taskService.getAllTasks();

        // Проверка
        assertEquals(2, result.size());
        assertTrue(result.contains(task1));
        assertTrue(result.contains(task2));
    }

    @Test
    void testCreateTask() {
        // Подготовка
        CreateTaskRequest request = new CreateTaskRequest("New Task");

        // Действие
        Task result = taskService.createTask(request);

        // Проверка
        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        assertFalse(result.isCompleted());
        assertNotNull(result.getCreatedAT());

        // Проверяем, что задача добавилась в репозиторий
        List<Task> allTasks = taskService.getAllTasks();
        assertEquals(1, allTasks.size());
        assertEquals(result, allTasks.get(0));
    }

    @Test
    void testUpdateTask() {
        // Подготовка
        Task task = taskService.createTask(new CreateTaskRequest("Old Task"));
        Integer taskId = task.getId();

        // Действие
        Task result = taskService.updateTask(taskId, true);

        // Проверка
        assertTrue(result.isCompleted());
        assertEquals("Old Task", result.getTitle());

        // Проверяем, что изменения сохранились
        Task updatedTask = taskService.getAllTasks().get(0);
        assertTrue(updatedTask.isCompleted());
    }

    @Test
    void testDeleteTaskSuccess() {
        // Подготовка
        Task task = taskService.createTask(new CreateTaskRequest("Test Task"));
        Integer taskId = task.getId();
        assertEquals(1, taskService.getAllTasks().size());

        // Действие
        boolean result = taskService.deleteTask(taskId);

        // Проверка
        assertTrue(result);
        assertEquals(0, taskService.getAllTasks().size());
    }

    @Test
    void testDeleteTaskNotFound() {
        // Действие
        boolean result = taskService.deleteTask(999);

        // Проверка
        assertFalse(result);
    }
}
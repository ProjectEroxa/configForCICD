package eroxa.study.configCICD.controller;

import eroxa.study.configCICD.model.Task;
import eroxa.study.configCICD.model.dto.CreateTaskRequest;
import eroxa.study.configCICD.model.dto.UpdateTaskRequest;
import eroxa.study.configCICD.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerTest {

    private TaskService taskService;
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(new eroxa.study.configCICD.repository.InMemoryTaskRepository());
        taskController = new TaskController(taskService);
    }

    @Test
    void testGetAllTasks() {
        // Подготовка
        taskService.createTask(new CreateTaskRequest("Task 1"));
        taskService.createTask(new CreateTaskRequest("Task 2"));

        // Действие
        List<Task> result = taskController.all();

        // Проверка
        assertEquals(2, result.size());
    }

    @Test
    void testCreateTask() {
        // Подготовка
        CreateTaskRequest request = new CreateTaskRequest("New Task");

        // Действие
        ResponseEntity<Task> response = taskController.createTask(request);

        // Проверка
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Task", response.getBody().getTitle());
    }

    @Test
    void testUpdateTask() {
        // Подготовка
        Task task = taskService.createTask(new CreateTaskRequest("Old Task"));
        UpdateTaskRequest request = new UpdateTaskRequest(true);

        // Действие
        ResponseEntity<Task> response = taskController.updateTask(task.getId(), request);

        // Проверка
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isCompleted());
    }

    @Test
    void testDeleteTaskSuccess() {
        // Подготовка
        Task task = taskService.createTask(new CreateTaskRequest("Test Task"));

        // Действие
        ResponseEntity<Void> response = taskController.deleteTask(task.getId());

        // Проверка
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteTaskNotFound() {
        // Действие
        ResponseEntity<Void> response = taskController.deleteTask(999);

        // Проверка
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
package eroxa.study.configCICD.controller;

import eroxa.study.configCICD.model.Task;
import eroxa.study.configCICD.model.dto.CreateTaskRequest;
import eroxa.study.configCICD.model.dto.UpdateTaskRequest;
import eroxa.study.configCICD.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //GET /tasks — получить все задачи
    @GetMapping
    public List<Task> all() {
        return taskService.getAllTasks();
    }

    //POST /tasks — добавить задачу
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task createdTask = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    //PUT /tasks/{id} — обновить задачу
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id,
                                           @RequestBody UpdateTaskRequest request) {
        Task updateedTask = taskService.updateTask(id, request.isCompleted());
        return ResponseEntity.status(HttpStatus.OK).body(updateedTask);
    }

    //DELETE /tasks/{id} — удалить задачу
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        if (taskService.deleteTask(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

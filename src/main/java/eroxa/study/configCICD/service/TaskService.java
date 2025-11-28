package eroxa.study.configCICD.service;

import eroxa.study.configCICD.model.Task;
import eroxa.study.configCICD.model.dto.CreateTaskRequest;
import eroxa.study.configCICD.repository.InMemoryTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {
    @Autowired
    InMemoryTaskRepository repository;
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public TaskService(InMemoryTaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.all();
    }

    //создает task и добавляет в repository
    public Task createTask(CreateTaskRequest request) {
        // Автоматическая генерация ID и даты
        Integer newId = idCounter.getAndIncrement();
        LocalDate createdAt = LocalDate.now();
        Task task = new Task(newId, request.getTitle(), false, createdAt);
        repository.save(task);
        return task;
    }

    public Task updateTask(Integer id, boolean completed) {
        Task updatedTask = repository.getById(id);
        updatedTask.setCompleted(completed);
        repository.save(updatedTask);
        return updatedTask;
    }

    public boolean deleteTask(Integer id) {
        try {
            repository.getById(id);
            return repository.removeById(id);
        } catch (Exception e) {
            return false;
        }
    }
}

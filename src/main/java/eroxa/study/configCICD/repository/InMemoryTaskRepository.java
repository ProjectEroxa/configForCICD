package eroxa.study.configCICD.repository;

import eroxa.study.configCICD.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryTaskRepository {
    private final ConcurrentHashMap<Integer, Task> taskRepository = new ConcurrentHashMap<>();
    private Long nextArchivedId = 1L;

    public List<Task> all() {
        return new ArrayList<>(taskRepository.values());
    }

    public Task getById(Integer id) {
        Task task = taskRepository.get(id);
        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found, incorrect id!");
        }
        return task;
    }

    public void save(Task task) {
        Long archivedId = nextArchivedId++;
        taskRepository.put(task.getId(), task);
    }

    public boolean removeById(Integer id) {
        return taskRepository.remove(id) != null;
    }
}

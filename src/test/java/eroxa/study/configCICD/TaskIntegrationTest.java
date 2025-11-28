package eroxa.study.configCICD;

import eroxa.study.configCICD.model.Task;
import eroxa.study.configCICD.model.dto.CreateTaskRequest;
import eroxa.study.configCICD.model.dto.UpdateTaskRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testFullTaskLifecycle() {
        // Create Task
        CreateTaskRequest createRequest = new CreateTaskRequest("Integration Test Task");
        ResponseEntity<Task> createResponse = restTemplate.postForEntity(
                "/tasks", createRequest, Task.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        Task createdTask = createResponse.getBody();

        Integer taskId = createdTask.getId();
        assertEquals("Integration Test Task", createdTask.getTitle());
        assertFalse(createdTask.isCompleted());

        // Get All Tasks
        ResponseEntity<Task[]> getAllResponse = restTemplate.getForEntity(
                "/tasks", Task[].class);

        assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
        assertTrue(getAllResponse.getBody().length >= 1);

        // Update Task
        UpdateTaskRequest updateRequest = new UpdateTaskRequest();
        updateRequest.setCompleted(true);

        ResponseEntity<Task> updateResponse = restTemplate.exchange(
                "/tasks/" + taskId, HttpMethod.PUT,
                new HttpEntity<>(updateRequest), Task.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertTrue(updateResponse.getBody().isCompleted());

        // Delete Task
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/tasks/" + taskId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
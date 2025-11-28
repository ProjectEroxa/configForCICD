package eroxa.study.configCICD.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateTaskRequest {
    private boolean completed;

    // Конструктор по умолчанию для Jackson
    public UpdateTaskRequest() {
    }

    // Конструктор с параметром
    @JsonCreator
    public UpdateTaskRequest(@JsonProperty("completed") boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

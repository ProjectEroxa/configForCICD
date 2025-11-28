package eroxa.study.configCICD.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTaskRequest {
    @NotBlank
    private final String title;

    // Конструктор для Jackson
    @JsonCreator
    public CreateTaskRequest(@JsonProperty("title") String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

package eroxa.study.configCICD.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    @Positive
    private final Integer id;
    @NotBlank
    @Size(min = 3, max = 255)
    private final String title;
    private boolean completed;
    @NotBlank
    private final LocalDate createdAT;

    public Task(Integer id, String title, boolean completed, LocalDate createdAT) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.createdAT = LocalDate.now();
    }

    // Геттеры
    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDate getCreatedAT() {
        return createdAT;
    }

    // Сеттер
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                ", createdAT=" + createdAT +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return completed == task.completed &&
                Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(createdAT, task.createdAT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, completed, createdAT);
    }
}

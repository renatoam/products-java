package com.example.todoapp.task.dtos;

import com.example.todoapp.task.models.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTodoDTO(@NotBlank String description, @NotNull Status status) {
}

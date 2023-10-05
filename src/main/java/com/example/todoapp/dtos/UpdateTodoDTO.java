package com.example.todoapp.dtos;

import com.example.todoapp.models.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTodoDTO(@NotBlank String description, @NotNull Status status) {
}

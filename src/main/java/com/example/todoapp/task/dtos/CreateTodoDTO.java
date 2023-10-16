package com.example.todoapp.task.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoDTO(@NotBlank String description) {
}

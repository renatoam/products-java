package com.example.todoapp.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoDTO(@NotBlank String description) {
}

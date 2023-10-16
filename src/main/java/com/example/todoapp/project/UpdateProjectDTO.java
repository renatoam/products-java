package com.example.todoapp.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateProjectDTO(@NotBlank UUID id, @NotBlank String name, @NotNull LocalDate dueDate) {
}

package com.example.todoapp.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateProjectDTO(
        @NotBlank String name,
        @NotNull LocalDate dueDate
        ) {
}

package com.example.todoapp.developer.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateDevDTO(
        @NotBlank String name,
        String stack,
        String expertise,
        UUID projectId
) {}

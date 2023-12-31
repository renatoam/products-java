package com.example.todoapp.developer.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateDevDTO(
        @NotBlank String name,
        @NotBlank String email,
        String stack,
        String expertise,
        UUID projectId
) {}

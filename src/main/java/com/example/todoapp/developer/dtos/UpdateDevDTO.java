package com.example.todoapp.developer.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UpdateDevDTO(
        @NotBlank String name,
        String stack,
        String expertise,
        UUID projectId
) {
}

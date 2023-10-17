package com.example.todoapp.account;

import jakarta.validation.constraints.NotBlank;

public record SignUpDTO(
        String name,
        @NotBlank String password,
        @NotBlank String email,
        String role
) {
}

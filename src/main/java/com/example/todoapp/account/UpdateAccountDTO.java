package com.example.todoapp.account;

import jakarta.validation.constraints.NotBlank;

public record UpdateAccountDTO(
        String name,
        String email,
        String role
) {
}

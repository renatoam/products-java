package com.example.todoapp.account;

import jakarta.validation.constraints.NotBlank;

public record SignInDTO(
        @NotBlank String email,
        @NotBlank String password
) {
}

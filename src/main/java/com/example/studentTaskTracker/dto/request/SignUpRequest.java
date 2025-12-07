package com.example.studentTaskTracker.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @Email(message = "Email должен быть корректным")
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}

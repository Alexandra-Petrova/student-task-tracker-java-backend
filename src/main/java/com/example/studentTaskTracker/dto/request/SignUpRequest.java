package com.example.studentTaskTracker.dto.request;

public record SignUpRequest(
        String email,
        String password,
        String firstName,
        String lastName
) {
}

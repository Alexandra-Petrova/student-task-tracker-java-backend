package com.example.studentTaskTracker.dto.request;

public record UserRequest(
        String firstName,
        String lastName,
        String email
) {
}

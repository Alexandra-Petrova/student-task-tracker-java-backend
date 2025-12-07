package com.example.studentTaskTracker.dto.response;

import com.example.studentTaskTracker.entity.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        LocalDateTime createdAt,
        String email,
        Role role
) {
}

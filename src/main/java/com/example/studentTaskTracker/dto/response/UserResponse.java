package com.example.studentTaskTracker.dto.response;

import com.example.studentTaskTracker.entity.Role;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        Role role
) {
}

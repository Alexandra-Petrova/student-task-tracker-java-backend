package com.example.studentTaskTracker.dto;

import com.example.studentTaskTracker.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponseDto(
        Long id,
        String name,
        String description,
        TaskStatus taskStatus,
        LocalDateTime deadline,
        Long groupId,
        String groupName,
        Long ownerId
) {
}

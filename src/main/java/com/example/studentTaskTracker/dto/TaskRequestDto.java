package com.example.studentTaskTracker.dto;

import com.example.studentTaskTracker.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskRequestDto(
        String name,
        String description,
        TaskStatus status,
        LocalDateTime deadline,
        Long groupId
) {}

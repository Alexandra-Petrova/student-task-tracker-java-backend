package com.example.studentTaskTracker.dto.request;

import com.example.studentTaskTracker.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskRequest(
        String name,
        String description,
        TaskStatus status,
        LocalDateTime deadline,
        Long groupId
) {}

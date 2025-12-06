package com.example.studentTaskTracker.mapper;

import com.example.studentTaskTracker.dto.TaskGroupRequestDto;
import com.example.studentTaskTracker.dto.TaskGroupResponseDto;
import com.example.studentTaskTracker.entity.TaskGroup;
import org.springframework.stereotype.Component;

@Component
public class TaskGroupMapper {

    public TaskGroupMapper(TaskMapper taskMapper) {
    }

    public TaskGroupResponseDto asResponse(TaskGroup group) {
        return new TaskGroupResponseDto(
                group.getId(),
                group.getName()
        );
    }

    public TaskGroup toEntity(TaskGroupRequestDto dto) {
        TaskGroup group = new TaskGroup();
        group.setName(dto.name());
        return group;
    }

}

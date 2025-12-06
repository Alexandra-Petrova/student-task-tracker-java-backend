package com.example.studentTaskTracker.mapper;

import com.example.studentTaskTracker.dto.TaskRequestDto;
import com.example.studentTaskTracker.dto.TaskResponseDto;
import com.example.studentTaskTracker.entity.Task;
import com.example.studentTaskTracker.entity.TaskGroup;
import com.example.studentTaskTracker.repository.TaskGroupRepository;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    private final TaskGroupRepository groupRepository;

    public TaskMapper(TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public TaskResponseDto asResponse(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getTaskStatus(),
                task.getDeadLine(),
                task.getGroup() != null ? task.getGroup().getId() : null,
                task.getGroup() != null ? task.getGroup().getName() : null,
                task.getUserOwner() != null ? task.getUserOwner().getId() : null
        );
    }

    public Task toEntity (TaskRequestDto dto) {
        Task task = new Task();
        task.setName(dto.name());
        task.setDescription(dto.description());
        task.setTaskStatus(dto.status());
        task.setDeadLine(dto.deadline());
        if (dto.groupId() != null) {
            TaskGroup group = groupRepository.findById(dto.groupId())
                    .orElseThrow(() -> new RuntimeException("Group not found"));
            task.setGroup(group);
        }
        return task;
    }
}

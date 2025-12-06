package com.example.studentTaskTracker.mapper;

import com.example.studentTaskTracker.dto.request.TaskGroupRequest;
import com.example.studentTaskTracker.dto.response.TaskGroupResponse;
import com.example.studentTaskTracker.entity.TaskGroup;
import org.springframework.stereotype.Component;

@Component
public class TaskGroupMapper {

    public TaskGroupMapper(TaskMapper taskMapper) {
    }

    public TaskGroupResponse asResponse(TaskGroup group) {
        return new TaskGroupResponse(
                group.getId(),
                group.getName()
        );
    }

    public TaskGroup toEntity(TaskGroupRequest dto) {
        TaskGroup group = new TaskGroup();
        group.setName(dto.name());
        return group;
    }

}

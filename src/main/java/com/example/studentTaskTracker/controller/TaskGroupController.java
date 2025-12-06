package com.example.studentTaskTracker.controller;

import com.example.studentTaskTracker.dto.request.TaskGroupRequest;
import com.example.studentTaskTracker.dto.response.TaskGroupResponse;
import com.example.studentTaskTracker.entity.TaskGroup;
import com.example.studentTaskTracker.mapper.TaskGroupMapper;
import com.example.studentTaskTracker.service.TaskGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-groups")
public class TaskGroupController {
    private final TaskGroupService groupService;
    private final TaskGroupMapper groupMapper;

    public TaskGroupController(TaskGroupService groupService, TaskGroupMapper groupMapper) {
        this.groupService = groupService;
        this.groupMapper = groupMapper;
    }

    @PostMapping
    public TaskGroupResponse create(@RequestBody TaskGroupRequest dto) {
        TaskGroup group = groupService.create(groupMapper.toEntity(dto));
        return groupMapper.asResponse(group);
    }

    @GetMapping("/{id}")
    public TaskGroupResponse getById(@PathVariable Long id) {
        TaskGroup group = groupService.getById(id);
        return groupMapper.asResponse(group);
    }

    @GetMapping
    public List<TaskGroupResponse> getAll() {
        return groupService.getAll().stream()
                .map(groupMapper::asResponse)
                .toList();
    }

    @PutMapping("/{id}")
    public TaskGroupResponse update(
            @PathVariable Long id,
            @RequestBody TaskGroupRequest dto) {

        TaskGroup group = groupMapper.toEntity(dto);
        TaskGroup updatedGroup = groupService.update(id, group);
        return groupMapper.asResponse(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }
}

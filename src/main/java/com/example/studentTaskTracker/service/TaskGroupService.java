package com.example.studentTaskTracker.service;

import com.example.studentTaskTracker.entity.TaskGroup;
import com.example.studentTaskTracker.repository.TaskGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskGroupService {
    private final TaskGroupRepository taskGroupRepository;

    public TaskGroupService(TaskGroupRepository taskGroupRepository) {
        this.taskGroupRepository = taskGroupRepository;
    }

    public TaskGroup create(TaskGroup group) {
        return taskGroupRepository.save(group);
    }

    public TaskGroup getById(Long id) {
        return taskGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("Task group not found"));
    }

    public TaskGroup update(Long id, TaskGroup updated) {
        TaskGroup existing = getById(id);
        existing.setName(updated.getName());
        return taskGroupRepository.save(existing);
    }

    public void delete(Long id) {
        taskGroupRepository.deleteById(id);
    }

    public List<TaskGroup> getAll() {
        return taskGroupRepository.findAll();
    }
}

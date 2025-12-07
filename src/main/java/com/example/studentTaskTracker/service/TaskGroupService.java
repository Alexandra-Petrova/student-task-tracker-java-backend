package com.example.studentTaskTracker.service;

import com.example.studentTaskTracker.entity.Role;
import com.example.studentTaskTracker.entity.Task;
import com.example.studentTaskTracker.entity.TaskGroup;
import com.example.studentTaskTracker.entity.User;
import com.example.studentTaskTracker.repository.TaskGroupRepository;
import com.example.studentTaskTracker.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskGroupService {
    private final TaskGroupRepository taskGroupRepository;
    private final UserRepository userRepository;

    public TaskGroupService(TaskGroupRepository taskGroupRepository, UserRepository userRepository) {
        this.taskGroupRepository = taskGroupRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException("User not found");
        return user;
    }

    public TaskGroup create(TaskGroup group) {
        User current = getCurrentUser();
        group.setUserOwner(current);
        return taskGroupRepository.save(group);
    }

    public TaskGroup getById(Long id) {
        TaskGroup group = taskGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task group not found"));
        User current = getCurrentUser();
        if (!group.getUserOwner().getId().equals(current.getId()) && current.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("No permission to view this group");
        }
        return group;
    }

    public TaskGroup update(Long id, TaskGroup updated) {
        TaskGroup existing = getById(id);
        existing.setName(updated.getName());
        return taskGroupRepository.save(existing);
    }

    public void delete(Long id) {
        TaskGroup existing = getById(id);
        taskGroupRepository.delete(existing);
    }

    public List<TaskGroup> getAll() {
        User current = getCurrentUser();
        if (current.getRole() == Role.ROLE_ADMIN) {
            return taskGroupRepository.findAll();
        }
        return taskGroupRepository.findAllByUserOwner(current);
    }
}

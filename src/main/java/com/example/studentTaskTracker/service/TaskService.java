package com.example.studentTaskTracker.service;

import com.example.studentTaskTracker.entity.Role;
import com.example.studentTaskTracker.entity.Task;
import com.example.studentTaskTracker.entity.TaskGroup;
import com.example.studentTaskTracker.entity.User;
import com.example.studentTaskTracker.repository.TaskGroupRepository;
import com.example.studentTaskTracker.repository.TaskRepository;
import com.example.studentTaskTracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskGroupRepository groupRepository;
    private final UserRepository userRepository;


    public TaskService(TaskRepository taskRepository, TaskGroupRepository groupRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if (user == null) throw new RuntimeException("User not found");
        return user;
    }

    public List<Task> getAll() {
        User current = getCurrentUser();
        return taskRepository.findAllByUserOwner(current);
    }

    public Task create(Task task) {
        User current = getCurrentUser();
        task.setUserOwner(current);
        return taskRepository.save(task);
    }

    public Task getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        User current = getCurrentUser();
        if (!task.getUserOwner().getId().equals(current.getId()) && current.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("No permission to view this task");
        }
        return task;
    }

    public List<Task> getByUser(User user) {
        User current = getCurrentUser();
        if (!current.getId().equals(user.getId()) && current.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("No permission to view tasks of this user");
        }
        return taskRepository.findAllByUserOwner(user);
    }

    @Transactional
    public Task update(Long id, Task updated) {
        Task existing = getById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setTaskStatus(updated.getTaskStatus());
        existing.setDeadLine(updated.getDeadLine());

        if (updated.getGroup() != null) {
            TaskGroup group = groupRepository.findById(updated.getGroup().getId())
                    .orElseThrow(() -> new RuntimeException("Task group not found"));
            User current = getCurrentUser();
            if (!group.getUserOwner().getId().equals(current.getId()) && current.getRole() != Role.ROLE_ADMIN) {
                throw new AccessDeniedException("Cannot assign task to someone else's group");
            }
            existing.setGroup(group);
        } else {
            existing.setGroup(null);
        }

        return taskRepository.save(existing);
    }

    public void delete(Long id) {
        Task existing = getById(id);
        taskRepository.delete(existing);
    }

    @Transactional
    public Task addTaskToGroup(Long taskId, Long groupId) {
        Task task = getById(taskId);
        TaskGroup group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Task group not found"));
        User current = getCurrentUser();
        if (!group.getUserOwner().getId().equals(current.getId()) && current.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("Cannot assign task to someone else's group");
        }
        task.setGroup(group);
        return task;
    }

    @Transactional
    public Task moveTaskToGroup(Long taskId, Long newGroupId) {
        return addTaskToGroup(taskId, newGroupId);
    }

    @Transactional
    public Task deleteTaskFromGroup(Long taskId) {
        Task task = getById(taskId);
        task.setGroup(null);
        return task;
    }

}

package com.example.studentTaskTracker.service;

import com.example.studentTaskTracker.entity.Task;
import com.example.studentTaskTracker.entity.TaskGroup;
import com.example.studentTaskTracker.repository.TaskGroupRepository;
import com.example.studentTaskTracker.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskGroupRepository groupRepository;


    public TaskService(TaskRepository taskRepository, TaskGroupRepository groupRepository) {
        this.taskRepository = taskRepository;
        this.groupRepository = groupRepository;
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task update(Long id, Task updated) {
        Task existing = getById(id);

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setTaskStatus(updated.getTaskStatus());
        existing.setDeadLine(updated.getDeadLine());
        existing.setGroup(updated.getGroup());

        return taskRepository.save(existing);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task addTaskToGroup(Long taskId, Long groupId) {
        Task task = getById(taskId);
        TaskGroup group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Task group not found"));
        task.setGroup(group);
        return task;
    }

    @Transactional
    public Task moveTaskToGroup(Long taskId, Long newGroupId) {
        Task task = getById(taskId);
        TaskGroup newGroup = groupRepository.findById(newGroupId).orElseThrow(() -> new RuntimeException("Task group not found"));
        task.setGroup(newGroup);
        return task;
    }

    @Transactional
    public Task deleteTaskFromGroup(Long taskId) {
        Task task = getById(taskId);
        task.setGroup(null);
        return task;
    }

}

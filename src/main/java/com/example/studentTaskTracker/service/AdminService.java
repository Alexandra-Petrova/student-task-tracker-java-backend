package com.example.studentTaskTracker.service;

import com.example.studentTaskTracker.dto.response.TaskGroupResponse;
import com.example.studentTaskTracker.dto.response.TaskResponse;
import com.example.studentTaskTracker.dto.response.UserResponse;
import com.example.studentTaskTracker.mapper.TaskGroupMapper;
import com.example.studentTaskTracker.mapper.TaskMapper;
import com.example.studentTaskTracker.mapper.UserMapper;
import com.example.studentTaskTracker.repository.TaskGroupRepository;
import com.example.studentTaskTracker.repository.TaskRepository;
import com.example.studentTaskTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskGroupRepository groupRepository;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    private final TaskGroupMapper groupMapper;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::asResponse)
                .toList();
    }

    public UserResponse getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::asResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<TaskResponse> getUserTasks(Long userId) {
        return taskRepository.findAllByUserOwner_Id(userId)
                .stream()
                .map(taskMapper::asResponse)
                .toList();
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::asResponse)
                .toList();
    }

    public List<TaskGroupResponse> getAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(groupMapper::asResponse)
                .toList();
    }

    public Object getStatByStatus() {
        return taskRepository.countTasksByStatus();
    }

    public Object getStatByGroups() {
        return taskRepository.countTasksByGroup();
    }
}


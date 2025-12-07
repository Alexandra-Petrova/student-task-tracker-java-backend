package com.example.studentTaskTracker.controller;

import com.example.studentTaskTracker.dto.response.TaskGroupResponse;
import com.example.studentTaskTracker.dto.response.TaskResponse;
import com.example.studentTaskTracker.dto.response.UserResponse;
import com.example.studentTaskTracker.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "Auth")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return adminService.getUserById(userId);
    }

    @GetMapping("/users/{userId}/tasks")
    public List<TaskResponse> getUserTasks(@PathVariable Long userId) {
        return adminService.getUserTasks(userId);
    }

    @GetMapping("/tasks")
    public List<TaskResponse> getAllTasks() {
        return adminService.getAllTasks();
    }

    @GetMapping("/groups")
    public List<TaskGroupResponse> getAllGroups() {
        return adminService.getAllGroups();
    }

    @GetMapping("/stats/status")
    public Object statsByStatus() {
        return adminService.getStatByStatus();
    }

    @GetMapping("/stats/groups")
    public Object statsByGroups() {
        return adminService.getStatByGroups();
    }
}

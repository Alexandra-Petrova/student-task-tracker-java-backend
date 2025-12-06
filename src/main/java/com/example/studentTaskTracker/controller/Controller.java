package com.example.studentTaskTracker.controller;

import com.example.studentTaskTracker.dto.request.UserRequest;
import com.example.studentTaskTracker.dto.response.DeletedResponse;
import com.example.studentTaskTracker.dto.response.UserResponse;
import com.example.studentTaskTracker.dto.response.exception.NotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@SecurityRequirement(name = "Auth")
public interface Controller {
    List<UserResponse> list();

    UserResponse getById(Long id) throws NotFoundException;

    UserResponse update(Long id, UserRequest request) throws NotFoundException;

    DeletedResponse delete(Long id) throws NotFoundException;

    void getAdmin() throws NotFoundException;
}

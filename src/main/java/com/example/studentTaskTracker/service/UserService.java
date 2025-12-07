package com.example.studentTaskTracker.service;

import com.example.studentTaskTracker.dto.request.UserRequest;
import com.example.studentTaskTracker.dto.response.UserResponse;
import com.example.studentTaskTracker.dto.response.exception.AlreadyExistsException;
import com.example.studentTaskTracker.dto.response.exception.NotFoundException;
import com.example.studentTaskTracker.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponse> list();

    UserResponse getById(Long id) throws NotFoundException;

    void create(User user) throws AlreadyExistsException;

    UserResponse update(Long id, UserRequest request) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}

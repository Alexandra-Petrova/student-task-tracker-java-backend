package com.example.studentTaskTracker.controller;

import com.example.studentTaskTracker.dto.request.UserRequest;
import com.example.studentTaskTracker.dto.response.DeletedResponse;
import com.example.studentTaskTracker.dto.response.UserResponse;
import com.example.studentTaskTracker.dto.response.exception.NotFoundException;
import com.example.studentTaskTracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerImpl implements Controller {

    public ControllerImpl(UserService service) {
        this.service = service;
    }

    private final UserService service;

    @Override
    @GetMapping("/user/api/get-admin")
    public void getAdmin() throws NotFoundException {
        service.getAdmin();
    }

    @GetMapping("/admin/api/user")
    @Override
    public List<UserResponse> list() {
        return service.list();
    }

    @GetMapping("/admin/api/user/{id}")
    @Override
    public UserResponse getById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }

    @Override
    @PutMapping("/admin/api/user/{id}")
    public UserResponse update(@PathVariable Long id, @RequestBody UserRequest request) throws NotFoundException {
        return service.update(id, request);
    }

    @DeleteMapping("/admin/api/user/{id}")
    @Override
    public DeletedResponse delete(@PathVariable Long id) throws NotFoundException {
        service.delete(id);
        return new DeletedResponse();
    }
}

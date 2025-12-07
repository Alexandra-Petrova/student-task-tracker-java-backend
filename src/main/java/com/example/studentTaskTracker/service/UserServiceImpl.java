package com.example.studentTaskTracker.service;

import com.example.studentTaskTracker.dto.request.UserRequest;
import com.example.studentTaskTracker.dto.response.UserResponse;
import com.example.studentTaskTracker.dto.response.exception.AlreadyExistsException;
import com.example.studentTaskTracker.dto.response.exception.NotFoundException;
import com.example.studentTaskTracker.entity.User;
import com.example.studentTaskTracker.mapper.UserMapper;
import com.example.studentTaskTracker.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserRepository repository;

    public UserServiceImpl(UserMapper mapper, UserRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<UserResponse> list() {
        return mapper.asListResponse(
                repository.findAll()
        );
    }

    @Override
    public UserResponse getById(Long id) throws NotFoundException {
        User user = repository.findById(id).orElseThrow(NotFoundException::new);
        return mapper.asResponse(user);
    }

    @Override
    public void create(User user) throws AlreadyExistsException {
        if (repository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("User with this email already exists.");
        }

        repository.save(user);
    }

    @Transactional
    @Modifying
    @Override
    public UserResponse update(Long id, @Valid UserRequest request) throws NotFoundException {
        User user = repository.findById(id).orElseThrow(NotFoundException::new);
        user = mapper.update(user, request);
        return mapper.asResponse(user);
    }

    @Transactional
    @Modifying
    @Override
    public void delete(Long id) throws NotFoundException {
        User user = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.delete(user);
    }
}

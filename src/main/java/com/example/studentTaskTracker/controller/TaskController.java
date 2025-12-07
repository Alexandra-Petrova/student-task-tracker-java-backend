package com.example.studentTaskTracker.controller;

import com.example.studentTaskTracker.dto.request.TaskRequest;
import com.example.studentTaskTracker.dto.response.TaskResponse;
import com.example.studentTaskTracker.entity.Task;
import com.example.studentTaskTracker.entity.User;
import com.example.studentTaskTracker.mapper.TaskMapper;
import com.example.studentTaskTracker.repository.UserRepository;
import com.example.studentTaskTracker.service.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Auth")
@RestController
@RequestMapping("/api/tasks")
//@RequiredArgsConstructor
public class TaskController {

//    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final UserRepository userService;

    public TaskController(TaskService taskService, TaskMapper taskMapper, UserRepository userService) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.userService = userService;
    }

//    @GetMapping("/listAll")
//    public List<Task> listAll() {
//        return taskRepository.findAll();
//    }

//    public record TaskDto(String name) {
//
//    }
//
//
//    @GetMapping // по аналогии out, delete
//    public List<Task> getAllTasks() {
//        return taskRepository.findAll(); // чтобы у объекта были все поля, сгенерированные в БД
//    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello, it's me!";
    }

    @PostMapping
    public TaskResponse create(@RequestBody TaskRequest dto) {
//        Task task = taskService.create(taskMapper.toEntity(dto));
//        return taskMapper.asResponse(task);
        Task task = taskMapper.toEntity(dto);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User owner = userService.findByEmail(email);

        task.setUserOwner(owner);

        Task saved = taskService.create(task);
        return taskMapper.asResponse(saved);

    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.asResponse(task);
    }

    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.getAll().stream()
                .map(taskMapper::asResponse)
                .toList();
    }

    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable Long id,
            @RequestBody TaskRequest dto) {

        Task task = taskMapper.toEntity(dto);
        Task updatedTask = taskService.update(id, task);
        return taskMapper.asResponse(updatedTask);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PostMapping("/{taskId}/group/{groupId}")
    public TaskResponse addToGroup(@PathVariable Long taskId, @PathVariable Long groupId) {
        Task task = taskService.addTaskToGroup(taskId, groupId);
        return taskMapper.asResponse(task);
    }

    @PutMapping("/{taskId}/group/{groupId}")
    public TaskResponse moveToGroup(@PathVariable Long taskId, @PathVariable Long groupId) {
        Task task = taskService.moveTaskToGroup(taskId, groupId);
        return taskMapper.asResponse(task);
    }

    @DeleteMapping("/{taskId}/group")
    public TaskResponse deleteFromGroup(@PathVariable Long taskId) {
        Task task = taskService.deleteTaskFromGroup(taskId);
        return taskMapper.asResponse(task);
    }
}

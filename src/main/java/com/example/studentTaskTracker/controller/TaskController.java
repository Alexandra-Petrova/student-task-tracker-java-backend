package com.example.studentTaskTracker.controller;

import com.example.studentTaskTracker.dto.TaskRequestDto;
import com.example.studentTaskTracker.dto.TaskResponseDto;
import com.example.studentTaskTracker.entity.Task;
import com.example.studentTaskTracker.mapper.TaskMapper;
import com.example.studentTaskTracker.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
//@RequiredArgsConstructor
public class TaskController {

//    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
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
    public TaskResponseDto create(@RequestBody TaskRequestDto dto) {
        Task task = taskService.create(taskMapper.toEntity(dto));
        return taskMapper.asResponse(task);
    }

    @GetMapping("/{id}")
    public TaskResponseDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.asResponse(task);
    }

    @GetMapping
    public List<TaskResponseDto> getAll() {
        return taskService.getAll().stream()
                .map(taskMapper::asResponse)
                .toList();
    }

    @PutMapping("/{id}")
    public TaskResponseDto update(
            @PathVariable Long id,
            @RequestBody TaskRequestDto dto) {

        Task task = taskMapper.toEntity(dto);
        Task updatedTask = taskService.update(id, task);
        return taskMapper.asResponse(updatedTask);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PostMapping("/{taskId}/group/{groupId}")
    public TaskResponseDto addToGroup(@PathVariable Long taskId, @PathVariable Long groupId) {
        Task task = taskService.addTaskToGroup(taskId, groupId);
        return taskMapper.asResponse(task);
    }

    @PutMapping("/{taskId}/group/{groupId}")
    public TaskResponseDto moveToGroup(@PathVariable Long taskId, @PathVariable Long groupId) {
        Task task = taskService.moveTaskToGroup(taskId, groupId);
        return taskMapper.asResponse(task);
    }

    @DeleteMapping("/{taskId}/group")
    public TaskResponseDto deleteFromGroup(@PathVariable Long taskId) {
        Task task = taskService.deleteTaskFromGroup(taskId);
        return taskMapper.asResponse(task);
    }
}

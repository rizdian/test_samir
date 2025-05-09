package com.riz.test_samir.controller;

import com.riz.test_samir.dto.TaskCreateDto;
import com.riz.test_samir.constans.TaskStatusEnum;
import com.riz.test_samir.service.TaskService;
import com.riz.test_samir.web.BaseController;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController extends BaseController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Object createTask(@RequestBody @Valid TaskCreateDto taskCreateDto) {
        taskService.create(taskCreateDto);
        return buildResponseDataCreated();
    }

    @PutMapping("/{id}")
    public Object updateTask(@PathVariable Long id, @RequestBody @Valid TaskCreateDto taskCreateDto) {
        return buildResponseGeneralSuccess(taskService.update(id, taskCreateDto));

    }

    @GetMapping("/{id}")
    public Object getTaskById(@PathVariable Long id) {
        return buildResponseGeneralSuccess(taskService.getTaskDtoById(id));
    }

    @PatchMapping("/{id}/status")
    public Object updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatusEnum status) {
        taskService.updateStatus(id, status);
        return buildResponseGeneralSuccess();
    }

    @GetMapping
    public Object getAllTasks(Pageable pageable) {
        return buildResponseGeneralSuccess(taskService.getAllTasks(pageable));
    }
}
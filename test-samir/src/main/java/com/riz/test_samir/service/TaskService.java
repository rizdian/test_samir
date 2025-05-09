package com.riz.test_samir.service;

import com.riz.test_samir.constans.TaskStatusEnum;
import com.riz.test_samir.dto.TaskCreateDto;
import com.riz.test_samir.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    void create(TaskCreateDto taskCreateDto);
    TaskDto update(Long id, TaskCreateDto taskCreateDto);
    TaskDto getTaskDtoById(Long id);
    void updateStatus(Long id, TaskStatusEnum status);
    Page<TaskDto> getAllTasks(Pageable pageable);
}

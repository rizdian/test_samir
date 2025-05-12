package com.riz.test_samir.service;

import com.riz.test_samir.constans.TaskStatusEnum;
import com.riz.test_samir.domain.User;
import com.riz.test_samir.dto.TaskCreateDto;
import com.riz.test_samir.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    void create(User user, TaskCreateDto taskCreateDto);
    TaskDto update(User user, Long id, TaskCreateDto taskCreateDto);
    TaskDto getTaskDtoById(Long id);
    void updateStatus(User user, Long id, TaskStatusEnum status);
    Page<TaskDto> getAllTasks(Pageable pageable);
}

package com.riz.test_samir.service.impl;

import com.riz.test_samir.constans.TaskStatusEnum;
import com.riz.test_samir.domain.Task;
import com.riz.test_samir.dto.TaskCreateDto;
import com.riz.test_samir.dto.TaskDto;
import com.riz.test_samir.mapper.TaskMapper;
import com.riz.test_samir.repository.TaskRepository;
import com.riz.test_samir.repository.UserInfoRepository;
import com.riz.test_samir.service.TaskService;
import com.riz.test_samir.web.exception.BadRequestException;
import com.riz.test_samir.web.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    public static final String TASK_NOT_FOUND = "Task not found";
    private final TaskRepository taskRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserInfoRepository userInfoRepository) {
        this.taskRepository = taskRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    @Transactional
    public void create(TaskCreateDto taskCreateDto) {
        validateTitle(taskCreateDto.getTitle());
        userInfoRepository.findById(taskCreateDto.getAssign()).orElseThrow(() -> new NotFoundException("User not found"));

        Task task = TaskMapper.INSTANCE.taskDtoToTaskCreate(taskCreateDto);
        taskRepository.save(task);

        addHistoryTask(task);
    }

    @Override
    @Transactional
    public TaskDto update(Long id, TaskCreateDto taskCreateDto) {
        validateTitle(taskCreateDto.getTitle());
        Task taskExisting = taskRepository.findById(id).orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND));
        userInfoRepository.findById(taskCreateDto.getAssign()).orElseThrow(() -> new NotFoundException("User not found"));

        // todo validate only task with updateBy same as login who can edit

        Task task = TaskMapper.INSTANCE.taskDtoToTaskUpdate(taskCreateDto, id);
        task.setCreatedBy(taskExisting.getCreatedBy());
        task = taskRepository.save(task);
        addHistoryTask(task);
        return TaskMapper.INSTANCE.taskToTaskDto(task);
    }

    @Override
    public TaskDto getTaskDtoById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND));
        return TaskMapper.INSTANCE.taskToTaskDto(task);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, TaskStatusEnum status) {
        // todo updateBy with user login  or add history update
        taskRepository.findById(id).orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND));
        taskRepository.updateTaskStatus(id, status);
        addHistoryTask(taskRepository.getReferenceById(id));
    }

    @Override
    public Page<TaskDto> getAllTasks(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        return new PageImpl<>(
                tasks.getContent().stream()
                        .map(TaskMapper.INSTANCE::taskToTaskDto)
                        .toList(),
                pageable,
                tasks.getTotalElements()
        );
    }

    private void validateTitle(String title) {
        if (taskRepository.existsTaskByTitleEqualsIgnoreCase(title)){
            throw new BadRequestException("Title already exists");
        }
    }

    private void addHistoryTask(Task task) {
        //todo add history table
    }

}

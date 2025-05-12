package com.riz.test_samir.service.impl;

import com.riz.test_samir.constans.TaskStatusEnum;
import com.riz.test_samir.domain.Task;
import com.riz.test_samir.domain.User;
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
    public void create(User user, TaskCreateDto taskCreateDto) {
        validateTitle(taskCreateDto.getTitle(), null);
        userInfoRepository.findById(taskCreateDto.getAssign()).orElseThrow(() -> new NotFoundException("User not found"));

        Task task = TaskMapper.INSTANCE.taskDtoToTaskCreate(taskCreateDto);
        taskRepository.save(task);

        addHistoryTask(task);
    }

    @Override
    @Transactional
    public TaskDto update(User user, Long id, TaskCreateDto taskCreateDto) {
        Task taskExisting = taskRepository.findTaskByIdAndCreatedById(id, user.getId()).orElseThrow(() -> new NotFoundException(TASK_NOT_FOUND));
        validateTitle(taskCreateDto.getTitle(), taskExisting.getTitle());
        userInfoRepository.findById(taskCreateDto.getAssign()).orElseThrow(() -> new NotFoundException("User not found"));

        Task task = TaskMapper.INSTANCE.taskDtoToTaskUpdate(taskCreateDto, id);
        task.setCreatedBy(taskExisting.getCreatedBy());
        task.setCreatedAt(taskExisting.getCreatedAt());
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
    public void updateStatus(User user, Long id, TaskStatusEnum status) {
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

    private void validateTitle(String title, String taskExistingTitle) {
        if (taskExistingTitle!= null && taskExistingTitle.equalsIgnoreCase(title)) return;
        if (taskRepository.existsTaskByTitleEqualsIgnoreCase(title)){
            throw new BadRequestException("Title already exists");
        }
    }

    private void addHistoryTask(Task task) {
        //todo add history table
    }

}

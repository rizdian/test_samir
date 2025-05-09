package com.riz.test_samir.dto;

import com.riz.test_samir.constans.TaskStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatusEnum status;
    private UserDto assign;
    private UserDto createdBy;
    private LocalDateTime createdAt;
}

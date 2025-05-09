package com.riz.test_samir.mapper;

import com.riz.test_samir.domain.Task;
import com.riz.test_samir.dto.TaskCreateDto;
import com.riz.test_samir.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDto taskToTaskDto(Task task);

    @Mapping(ignore = true, target = "taskCreateDto.status")
    @Mapping(source = "taskCreateDto.assign", target = "assign.id")
    @Mapping(source = "id", target = "id")
    Task taskDtoToTaskUpdate(TaskCreateDto taskCreateDto, Long id);

    @Mapping(source = "assign", target = "assign.id")
    Task taskDtoToTaskCreate(TaskCreateDto taskCreateDto);



}

package com.riz.test_samir.mapper;

import com.riz.test_samir.domain.Task;
import com.riz.test_samir.domain.User;
import com.riz.test_samir.dto.TaskCreateDto;
import com.riz.test_samir.dto.TaskDto;
import com.riz.test_samir.dto.UserDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-09T23:17:06+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.15 (Microsoft)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto taskToTaskDto(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDto taskDto = new TaskDto();

        taskDto.setTitle( task.getTitle() );
        taskDto.setDescription( task.getDescription() );
        taskDto.setAssign( userToUserDto( task.getAssign() ) );
        taskDto.setCreatedBy( userToUserDto( task.getCreatedBy() ) );
        taskDto.setCreatedAt( task.getCreatedAt() );

        return taskDto;
    }

    @Override
    public Task taskDtoToTaskUpdate(TaskCreateDto taskCreateDto, Long id) {
        if ( taskCreateDto == null && id == null ) {
            return null;
        }

        Task task = new Task();

        if ( taskCreateDto != null ) {
            task.setAssign( taskCreateDtoToUser( taskCreateDto ) );
            task.setTitle( taskCreateDto.getTitle() );
            task.setDescription( taskCreateDto.getDescription() );
        }
        task.setId( id );

        return task;
    }

    @Override
    public Task taskDtoToTaskCreate(TaskCreateDto taskCreateDto) {
        if ( taskCreateDto == null ) {
            return null;
        }

        Task task = new Task();

        task.setAssign( taskCreateDtoToUser1( taskCreateDto ) );
        task.setTitle( taskCreateDto.getTitle() );
        task.setDescription( taskCreateDto.getDescription() );

        return task;
    }

    protected UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setUsername( user.getUsername() );

        return userDto;
    }

    protected User taskCreateDtoToUser(TaskCreateDto taskCreateDto) {
        if ( taskCreateDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( taskCreateDto.getAssign() );

        return user;
    }

    protected User taskCreateDtoToUser1(TaskCreateDto taskCreateDto) {
        if ( taskCreateDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( taskCreateDto.getAssign() );

        return user;
    }
}

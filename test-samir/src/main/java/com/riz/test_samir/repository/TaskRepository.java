package com.riz.test_samir.repository;

import com.riz.test_samir.constans.TaskStatusEnum;
import com.riz.test_samir.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
    void updateTaskStatus(@Param("id") Long id, @Param("status") TaskStatusEnum status);

    boolean existsTaskByTitleEqualsIgnoreCase(String title);

    Optional<Task> findTaskByIdAndCreatedById(Long id, Long createdById);
}

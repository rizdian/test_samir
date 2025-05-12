package com.riz.test_samir.repository;

import com.riz.test_samir.domain.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
}

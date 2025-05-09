package com.riz.test_samir.domain;

import com.riz.test_samir.constans.TaskStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatusEnum status = TaskStatusEnum.PENDING;

    @ManyToOne
    @JoinColumn(name = "assign")
    private User assign;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.status = TaskStatusEnum.PENDING;
        if (this.createdBy == null) {
            User currentUser = new User();
            currentUser.setId(2L);
            this.createdBy = currentUser;
        }
    }
}

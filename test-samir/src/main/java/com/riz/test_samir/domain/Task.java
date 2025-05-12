package com.riz.test_samir.domain;

import com.riz.test_samir.constans.TaskStatusEnum;
import com.riz.test_samir.filter.UserContext;
import com.riz.test_samir.web.exception.BadRequestException;
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
        User user = UserContext.getUser();
        if (user.getId() == null){
            throw new BadRequestException("User not found");
        }
        this.createdBy = user;
        this.createdAt = LocalDateTime.now();
    }
}

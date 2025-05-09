CREATE DATABASE task_management;

--todo pake JWT
CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE tasks
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    status      VARCHAR(255) DEFAULT 'PENDING',
    assign      BIGINT       NOT NULL,
    created_by  BIGINT       NOT NULL,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_created_by_task_user FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_assign_task_user FOREIGN KEY (assign) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_tasks_created_by ON tasks (created_by);
CREATE INDEX idx_tasks_assign ON tasks (assign);
CREATE INDEX idx_tasks_status ON tasks (status);

CREATE TABLE task_history
(
    id          BIGSERIAL PRIMARY KEY,
    task_id     BIGINT NOT NULL,
    status      VARCHAR(255),
    description TEXT,
    assign      BIGINT,
    changed_by  BIGINT NOT NULL,
    changed_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_task_history_task FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE,
    CONSTRAINT fk_task_history_user FOREIGN KEY (changed_by) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX idx_task_history_task_id ON task_history (task_id);

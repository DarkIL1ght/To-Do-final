CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       completed BOOLEAN DEFAULT false,
                       due_date TIMESTAMP,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       user_id BIGINT NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE tags (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL UNIQUE,
                      color VARCHAR(7) DEFAULT '#007bff',
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE task_tags (
                           task_id BIGINT NOT NULL,
                           tag_id BIGINT NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (task_id, tag_id),
                           FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
                           FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);
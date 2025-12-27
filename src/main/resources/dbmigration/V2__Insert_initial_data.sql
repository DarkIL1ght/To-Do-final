INSERT INTO users (username, email, password) VALUES
                                                  ('admin', 'admin@todo.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeG3ZhYp7XyQ8bF6F9h7QrJf4oQYQjJ1a'),
                                                  ('john_doe', 'john@example.com', '$2a$10$2B3c4D5e6F7g8H9i0J1k2L3m4N5o6P7q8R9s0T1u2V3w4X5y6Z7'),
                                                  ('jane_smith', 'jane@example.com', '$2a$10$A1b2C3d4E5f6G7h8I9j0K1l2M3n4O5p6Q7r8S9t0U1v2W3x4Y5z6');

INSERT INTO tags (name, color) VALUES
                                   ('Urgent', '#dc3545'),
                                   ('Work', '#007bff'),
                                   ('Personal', '#28a745'),
                                   ('Study', '#ffc107'),
                                   ('Home', '#17a2b8');

INSERT INTO tasks (title, description, user_id, due_date) VALUES
                                                              ('Complete Spring Boot project', 'Finish the final project for course', 2, '2024-12-31 23:59:59'),
                                                              ('Buy groceries', 'Milk, eggs, bread, fruits', 3, '2024-12-05 18:00:00'),
                                                              ('Learn Docker', 'Study containerization', 2, '2024-12-15 12:00:00');

INSERT INTO task_tags (task_id, tag_id) VALUES
                                            (1, 1), (1, 2), (1, 4),
                                            (2, 3),
                                            (3, 2), (3, 4);
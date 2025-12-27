package todo.service;

import todo.dto.DtoTask;
import todo.model.Task;
import todo.model.User;
import todo.model.Tag;
import todo.repository.TaskRepository;
import todo.repository.UserRepository;
import todo.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    private DtoTask toDto(Task task) {
        List<Long> tagIds = task.getTags() != null ?
                task.getTags().stream().map(Tag::getId).collect(Collectors.toList()) :
                List.of();

        return new DtoTask(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUser() != null ? task.getUser().getId() : null,
                tagIds
        );
    }

    private Task toEntity(DtoTask dtoTask) {
        Task task = new Task();
        task.setId(dtoTask.getId());
        task.setTitle(dtoTask.getTitle());
        task.setDescription(dtoTask.getDescription());
        task.setCompleted(dtoTask.isCompleted());
        task.setDueDate(dtoTask.getDueDate());
        task.setCreatedAt(dtoTask.getCreatedAt() != null ?
                dtoTask.getCreatedAt() : LocalDateTime.now());

        if (dtoTask.getUserId() != null) {
            User user = userRepository.findById(dtoTask.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            task.setUser(user);
        }

        if (dtoTask.getTagIds() != null && !dtoTask.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(dtoTask.getTagIds());
            task.setTags(tags);
        }

        return task;
    }

    public List<DtoTask> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DtoTask getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public List<DtoTask> getTasksByUser(Long userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DtoTask createTask(DtoTask dtoTask) {
        Task task = toEntity(dtoTask);
        Task savedTask = taskRepository.save(task);
        return toDto(savedTask);
    }

    public DtoTask updateTask(Long id, DtoTask dtoTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(dtoTask.getTitle());
        task.setDescription(dtoTask.getDescription());
        task.setCompleted(dtoTask.isCompleted());
        task.setDueDate(dtoTask.getDueDate());

        Task updatedTask = taskRepository.save(task);
        return toDto(updatedTask);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        taskRepository.deleteById(id);
    }
}
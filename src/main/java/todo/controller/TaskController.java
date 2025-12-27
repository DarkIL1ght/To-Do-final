package todo.controller;

import todo.dto.DtoTask;
import todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<DtoTask>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoTask> getTaskById(@PathVariable Long id) {
        DtoTask task = taskService.getTaskById(id);
        return task != null ?
                ResponseEntity.ok(task) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DtoTask>> getTasksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.getTasksByUser(userId));
    }

    @PostMapping
    public ResponseEntity<DtoTask> createTask(@RequestBody DtoTask dtoTask) {
        try {
            DtoTask createdTask = taskService.createTask(dtoTask);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoTask> updateTask(
            @PathVariable Long id,
            @RequestBody DtoTask dtoTask) {
        try {
            DtoTask updatedTask = taskService.updateTask(id, dtoTask);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
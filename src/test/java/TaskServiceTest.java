
import todo.dto.DtoTask;
import todo.model.Task;
import todo.model.User;
import todo.repository.TaskRepository;
import todo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private DtoTask dtoTask;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "testuser", "test@email.com", "password", null, null, null);
        task = new Task(1L, "Test Task", "Task Description", false,
                LocalDateTime.now().plusDays(1), LocalDateTime.now(), user, null);
        dtoTask = new DtoTask(1L, "Test Task", "Task Description", false,
                LocalDateTime.now().plusDays(1), LocalDateTime.now(), 1L, List.of());
    }

    @Test
    void getAllTasks_ShouldReturnTaskList() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<DtoTask> result = taskService.getAllTasks();

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTasksByUser_ShouldReturnUserTasks() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findByUserId(1L)).thenReturn(tasks);

        List<DtoTask> result = taskService.getTasksByUser(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
    }

    @Test
    void createTask_WithValidData_ShouldCreateTask() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        DtoTask result = taskService.createTask(dtoTask);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTask_WhenTaskExists_ShouldUpdateTask() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        dtoTask.setTitle("Updated Task");
        dtoTask.setDescription("Updated Description");

        DtoTask result = taskService.updateTask(1L, dtoTask);

        assertNotNull(result);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTask_WhenTaskExists_ShouldDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}
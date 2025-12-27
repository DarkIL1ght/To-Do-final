package todo.service.implement;

import todo.dto.DtoTask;
import java.util.List;

public interface TaskServiceImplement {

    List<DtoTask> getAllTasks();

    DtoTask getTaskById(Long id);

    List<DtoTask> getTasksByUser(Long userId);

    DtoTask createTask(DtoTask dtoTask);

    DtoTask updateTask(Long id, DtoTask dtoTask);

    void deleteTask(Long id);
}

package todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoTask {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private Long userId;
    private List<Long> tagIds;
}
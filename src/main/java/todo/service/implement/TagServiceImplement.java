package todo.service.implement;
import todo.dto.DtoTag;
import java.util.List;

public interface TagServiceImplement {

    List<DtoTag> getAllTags();

    DtoTag getTagById(Long id);

    DtoTag getTagByName(String name);

    DtoTag createTag(DtoTag dtoTag);

    DtoTag updateTag(Long id, DtoTag dtoTag);

    void deleteTag(Long id);
}

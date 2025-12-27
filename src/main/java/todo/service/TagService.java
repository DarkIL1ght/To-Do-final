package todo.service;

import todo.dto.DtoTag;
import todo.model.Tag;
import todo.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import todo.service.implement.TagServiceImplement;
@Service
@RequiredArgsConstructor
public class TagService implements TagServiceImplement{
    private final TagRepository tagRepository;

    private DtoTag toDto(Tag tag) {
        return new DtoTag(
                tag.getId(),
                tag.getName(),
                tag.getColor()
        );
    }

    private Tag toEntity(DtoTag dtoTag) {
        Tag tag = new Tag();
        tag.setId(dtoTag.getId());
        tag.setName(dtoTag.getName());
        tag.setColor(dtoTag.getColor());
        return tag;
    }
    @Override
    public List<DtoTag> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public DtoTag getTagById(Long id) {
        return tagRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }
    @Override
    public DtoTag getTagByName(String name) {
        return tagRepository.findByName(name)
                .map(this::toDto)
                .orElse(null);
    }
    @Override
    public DtoTag createTag(DtoTag dtoTag) {
        if (tagRepository.existsByName(dtoTag.getName())) {
            throw new RuntimeException("Tag with this name already exists");
        }

        Tag tag = toEntity(dtoTag);
        Tag savedTag = tagRepository.save(tag);
        return toDto(savedTag);
    }
    @Override
    public DtoTag updateTag(Long id, DtoTag dtoTag) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        tag.setName(dtoTag.getName());
        tag.setColor(dtoTag.getColor());

        Tag updatedTag = tagRepository.save(tag);
        return toDto(updatedTag);
    }
    @Override
    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found");
        }
        tagRepository.deleteById(id);
    }
}
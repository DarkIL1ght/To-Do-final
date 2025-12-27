package todo.mapper;

import todo.dto.DtoUser;
import todo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public DtoUser toDto(User user) {
        if (user == null) return null;

        return new DtoUser(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public User toEntity(DtoUser dtoUser) {
        if (dtoUser == null) return null;

        User user = new User();
        user.setId(dtoUser.getId());
        user.setUsername(dtoUser.getUsername());
        user.setEmail(dtoUser.getEmail());
        user.setPassword(dtoUser.getPassword());

        return user;
    }
}
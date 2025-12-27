package todo.service.implement;

import todo.dto.DtoUser;
import java.util.List;

public interface UserServiceImplement {

    List<DtoUser> getAllUsers();

    DtoUser getUserById(Long id);

    DtoUser createUser(DtoUser dtoUser);

    DtoUser updateUser(Long id, DtoUser dtoUser);

    void deleteUser(Long id);
}

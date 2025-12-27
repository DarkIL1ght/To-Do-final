
import todo.dto.DtoUser;
import todo.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    @Test
    void toDto_ShouldConvertEntityToDto() {

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@email.com");
        user.setPassword("password123");


        DtoUser dtoUser = userMapper.toDto(user);


        assertNotNull(dtoUser);
        assertEquals(1L, dtoUser.getId());
        assertEquals("testuser", dtoUser.getUsername());
        assertEquals("test@email.com", dtoUser.getEmail());
        assertEquals("password123", dtoUser.getPassword());
    }

    @Test
    void toDto_WhenEntityIsNull_ShouldReturnNull() {

        DtoUser result = userMapper.toDto(null);


        assertNull(result);
    }

    @Test
    void toEntity_ShouldConvertDtoToEntity() {

        DtoUser dtoUser = new DtoUser(1L, "testuser", "test@email.com", "password123");


        User user = userMapper.toEntity(dtoUser);


        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@email.com", user.getEmail());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void toEntity_WhenDtoIsNull_ShouldReturnNull() {

        User result = userMapper.toEntity(null);


        assertNull(result);
    }
}

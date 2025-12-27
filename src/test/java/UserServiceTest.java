package todo.service;

import todo.dto.DtoUser;
import todo.mapper.UserMapper;
import todo.model.User;
import todo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private DtoUser dtoUser;

    @BeforeEach
    void setUp() {
        user = new User(1L, "testuser", "test@email.com", "encodedPassword", null, null, null);
        dtoUser = new DtoUser(1L, "testuser", "test@email.com", "password123");
    }

    @Test
    void getAllUsers_ShouldReturnUserList() {

        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(user)).thenReturn(dtoUser);


        List<DtoUser> result = userService.getAllUsers();


        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(dtoUser);


        DtoUser result = userService.getUserById(1L);


        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void getUserById_WhenUserNotExists_ShouldReturnNull() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());


        DtoUser result = userService.getUserById(99L);

        assertNull(result);
    }

    @Test
    void createUser_WithValidData_ShouldCreateUser() {
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@email.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userMapper.toEntity(any(DtoUser.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(dtoUser);


        DtoUser result = userService.createUser(dtoUser);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_WithExistingUsername_ShouldThrowException() {
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);
        dtoUser.setUsername("existinguser");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(dtoUser);
        });

        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_WhenUserNotExists_ShouldThrowException() {
        when(userRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(99L);
        });
        assertEquals("User not found", exception.getMessage());
    }
}
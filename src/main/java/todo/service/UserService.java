package todo.service;

import todo.dto.DtoUser;
import todo.mapper.UserMapper;
import todo.model.User;
import todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import todo.service.implement.UserServiceImplement;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImplement {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<DtoUser> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public DtoUser getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }
    @Override
    public DtoUser createUser(DtoUser dtoUser) {
        if (userRepository.existsByUsername(dtoUser.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(dtoUser.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = userMapper.toEntity(dtoUser);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
    @Override
    public DtoUser updateUser(Long id, DtoUser dtoUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dtoUser.getUsername());
        user.setEmail(dtoUser.getEmail());

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
package todo.controller;

import todo.dto.DtoUser;
import todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<DtoUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoUser> getUserById(@PathVariable Long id) {
        DtoUser user = userService.getUserById(id);
        return user != null ?
                ResponseEntity.ok(user) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DtoUser> createUser(@RequestBody DtoUser dtoUser) {
        try {
            DtoUser createdUser = userService.createUser(dtoUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoUser> updateUser(
            @PathVariable Long id,
            @RequestBody DtoUser dtoUser) {
        try {
            DtoUser updatedUser = userService.updateUser(id, dtoUser);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
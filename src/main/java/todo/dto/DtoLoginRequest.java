package todo.dto;

import lombok.Data;

@Data
public class DtoLoginRequest {
    private String username;
    private String password;
}
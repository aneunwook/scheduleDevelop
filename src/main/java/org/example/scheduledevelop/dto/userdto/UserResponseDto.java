package org.example.scheduledevelop.dto.userdto;

import lombok.Getter;
import org.example.scheduledevelop.entity.User;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String email;
    private final String username;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}

package org.example.scheduledevelop.user.dto;

import lombok.Getter;
import org.example.scheduledevelop.user.entity.User;

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

package org.example.scheduledevelop.dto.userdto;

import lombok.Getter;
import org.example.scheduledevelop.entity.User;

import java.time.LocalDateTime;

@Getter
public class SignupResponseDto {
    private final Long id;
    private final String email;
    private final String username;
    private final LocalDateTime createdAt;

    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.createdAt = user.getCreatedAt();
    }
}

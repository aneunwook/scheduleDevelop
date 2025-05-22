package org.example.scheduledevelop.user.dto;

import lombok.Getter;
import org.example.scheduledevelop.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class SignupResponseDto {
    private final Long id;
    private final String email;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SignupResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }


}

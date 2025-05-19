package org.example.scheduledevelop.dto.userdto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private final String email;
    private final String username;

    public SignupRequestDto(String email, String username) {
        this.email = email;
        this.username = username;
    }
}

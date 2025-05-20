package org.example.scheduledevelop.dto.userdto;

import lombok.Getter;

@Getter
public class UpdateUserPasswordDto {

    private final String oldPassword;

    private final String newPassword;

    public UpdateUserPasswordDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}

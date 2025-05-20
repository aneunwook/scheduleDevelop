package org.example.scheduledevelop.dto.userdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserPasswordDto {

    private String oldPassword;

    private String newPassword;

}

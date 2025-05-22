package org.example.scheduledevelop.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력입니다.")
    @Size(max = 4, message = "유저명은 4글자 이내로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
}

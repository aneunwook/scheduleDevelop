package org.example.scheduledevelop.dto.scheduledto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScheduleRequestDto {

    @NotBlank(message = "제목은 필수 입력입니다.")
    @Size(max = 10, message = "제목은 10글자 이내로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용은 필수 입력입니다.")
    @Size(max = 100, message = "내용은 100글자 이내로 입력해주세요.")
    private String contents;

}

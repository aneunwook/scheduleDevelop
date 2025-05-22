package org.example.scheduledevelop.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    @NotBlank(message = "댓글은 필수 입력입니다")
    @Size(max = 100, message = "댓글 내용은 100글자 이내로 입력해주세요.")
    private String comment;
}

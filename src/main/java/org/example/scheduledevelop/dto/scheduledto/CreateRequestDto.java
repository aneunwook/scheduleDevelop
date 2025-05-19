package org.example.scheduledevelop.dto.scheduledto;

import lombok.Getter;

@Getter
public class CreateRequestDto {

    private final String title;

    private final String contents;

    private final Long userId;

    public CreateRequestDto(String title, String contents, Long userId) {
        this.title = title;
        this.contents = contents;
        this.userId = userId;
    }
}

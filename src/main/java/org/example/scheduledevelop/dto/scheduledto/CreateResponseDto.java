package org.example.scheduledevelop.dto.scheduledto;

import lombok.Getter;

@Getter
public class CreateResponseDto {

    private final Long id;
    private final String username;
    private final String title;
    private final String contents;

    public CreateResponseDto(Long id, String username, String title, String contents) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.contents = contents;
    }
}

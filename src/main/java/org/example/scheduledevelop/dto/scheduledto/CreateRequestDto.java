package org.example.scheduledevelop.dto.scheduledto;

import lombok.Getter;

@Getter
public class CreateRequestDto {

    private final String username;

    private final String title;

    private final String contents;

    public CreateRequestDto(String username, String title, String contents) {
        this.username = username;
        this.title = title;
        this.contents = contents;
    }
}

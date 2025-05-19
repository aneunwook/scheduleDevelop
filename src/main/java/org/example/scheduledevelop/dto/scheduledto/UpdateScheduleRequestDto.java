package org.example.scheduledevelop.dto.scheduledto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UpdateScheduleRequestDto {
    private final String title;

    private final String contents;

    private final Long userId;

    public UpdateScheduleRequestDto(String title, String contents, Long userId) {
        this.title = title;
        this.contents = contents;
        this.userId = userId;
    }
}

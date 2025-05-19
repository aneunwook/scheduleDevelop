package org.example.scheduledevelop.dto.scheduledto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UpdateScheduleRequestDto {
    private final String title;

    private final String contents;

    public UpdateScheduleRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}

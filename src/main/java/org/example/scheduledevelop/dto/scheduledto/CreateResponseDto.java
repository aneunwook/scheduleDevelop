package org.example.scheduledevelop.dto.scheduledto;

import lombok.Getter;
import org.example.scheduledevelop.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class CreateResponseDto {

    private final Long id;
    private final String username;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;

    public CreateResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedAt();
    }
}

package org.example.scheduledevelop.dto.scheduledto;

import lombok.Getter;
import org.example.scheduledevelop.dto.userdto.UserResponseDto;
import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.entity.User;

import java.time.LocalDateTime;

@Getter
public class CreateResponseDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;

    private final UserResponseDto user;

    public CreateResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedAt();
        this.user = new UserResponseDto(schedule.getUser());
    }
}

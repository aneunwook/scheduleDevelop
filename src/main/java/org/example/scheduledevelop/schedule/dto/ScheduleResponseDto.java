package org.example.scheduledevelop.schedule.dto;

import lombok.Getter;
import org.example.scheduledevelop.user.dto.UserResponseDto;
import org.example.scheduledevelop.schedule.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final int commentCount;

    private final UserResponseDto user;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
        this.commentCount = schedule.getComments().size();

        this.user = new UserResponseDto(schedule.getUser());
    }

    public static ScheduleResponseDto toDto(Schedule schedule) {
        return new ScheduleResponseDto(schedule);
    }
}

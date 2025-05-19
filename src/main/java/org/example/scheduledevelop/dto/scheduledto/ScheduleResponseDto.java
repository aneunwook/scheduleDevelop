package org.example.scheduledevelop.dto.scheduledto;

import lombok.Getter;
import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.entity.User;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private final User user;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();;
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();

        this.user = schedule.getUser();
    }

    public static ScheduleResponseDto toDto(Schedule schedule) {
        return new ScheduleResponseDto(schedule);
    }
}

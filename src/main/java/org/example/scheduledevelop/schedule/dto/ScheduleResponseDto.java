package org.example.scheduledevelop.schedule.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.example.scheduledevelop.comment.dto.CommentResponseDto;
import org.example.scheduledevelop.comment.entity.Comment;
import org.example.scheduledevelop.user.dto.UserResponseDto;
import org.example.scheduledevelop.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@JsonPropertyOrder({
        "id",
        "title",
        "contents",
        "createdAt",
        "updatedAt",
        "user",
        "comments"
})
@Getter
public class ScheduleResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private final UserResponseDto user;
    private final List<CommentResponseDto> comments;

    public ScheduleResponseDto(Schedule schedule, List<Comment> comments) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();

        this.user = new UserResponseDto(schedule.getUser());
        this.comments = comments.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public static ScheduleResponseDto toDto(Schedule schedule, List<Comment> comments) {
        return new ScheduleResponseDto(schedule, comments);
    }
}

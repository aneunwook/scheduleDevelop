package org.example.scheduledevelop.comment.dto;

import lombok.Getter;
import org.example.scheduledevelop.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResponseDto {
    private final Long id;
    private final Long scheduleId;
    private final String comment;
    private final String username;
    private final LocalDateTime createdAt;

    public CommentCreateResponseDto(Comment comment) {
        this.id = comment.getId();
        this.scheduleId = comment.getSchedule().getId();
        this.comment = comment.getComment();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
    }
}

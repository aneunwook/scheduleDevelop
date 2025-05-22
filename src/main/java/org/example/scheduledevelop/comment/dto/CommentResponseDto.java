package org.example.scheduledevelop.comment.dto;

import lombok.Getter;
import org.example.scheduledevelop.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long id;
    private final Long scheduleId;
    private final String comment;
    private final String username;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.scheduleId = comment.getSchedule().getId();
        this.comment = comment.getComment();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}

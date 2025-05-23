package org.example.scheduledevelop.comment.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.comment.dto.CommentCreateRequestDto;
import org.example.scheduledevelop.comment.dto.CommentCreateResponseDto;
import org.example.scheduledevelop.comment.dto.CommentResponseDto;
import org.example.scheduledevelop.comment.dto.CommentUpdateRequestDto;
import org.example.scheduledevelop.comment.entity.Comment;
import org.example.scheduledevelop.comment.repository.CommentRepository;
import org.example.scheduledevelop.exception.CommentNotFoundException;
import org.example.scheduledevelop.exception.ForbiddenException;
import org.example.scheduledevelop.exception.ScheduleNotFoundException;
import org.example.scheduledevelop.exception.UserNotFoundException;
import org.example.scheduledevelop.schedule.entity.Schedule;
import org.example.scheduledevelop.schedule.repository.ScheduleRepository;
import org.example.scheduledevelop.user.entity.User;
import org.example.scheduledevelop.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public CommentCreateResponseDto createComment(Long userId, Long scheduleId, CommentCreateRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다."));

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        Comment comments = new Comment(dto.getComment(), user, schedule);
        Comment commentSave = commentRepository.save(comments);

        return new CommentCreateResponseDto(commentSave);
    }

    public List<CommentResponseDto> getCommentsByScheduleId(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        List<Comment> comments = commentRepository.findByScheduleId(schedule.getId());

        return comments.stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public CommentResponseDto updateComment(Long userId, Long commentId, CommentUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!user.getId().equals(comment.getUser().getId())){
            throw new ForbiddenException("해당 게시물을 수정 할 수 없습니다.");
        }

        comment.setComment(requestDto.getComment());

        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!user.getId().equals(comment.getUser().getId())){
            throw new ForbiddenException("해당 게시물을 삭제 할 수 없습니다.");
        }

        commentRepository.delete(comment);

    }
}

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

    /**
     * 댓글 생성
     *
     * @param userId 댓글을 작성하는 사용자 ID
     * @param scheduleId 댓글이 작성될 일정 ID
     * @param dto 댓글 생성 요청 DTO
     * @return 생성된 댓글의 응답 DTO
     */
    public CommentCreateResponseDto createComment(Long userId, Long scheduleId, CommentCreateRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다."));

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        Comment comments = new Comment(dto.getComment(), user, schedule);
        Comment commentSave = commentRepository.save(comments);

        return new CommentCreateResponseDto(commentSave);
    }

    /**
     * 해당 일정에 있는 댓글 전체 조회
     *
     * @param scheduleId 조회할 일정 ID
     * @return 해당 일정에 달린 댓글 리스트
     */
    public List<CommentResponseDto> getCommentsByScheduleId(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("해당 일정이 존재하지 않습니다."));

        List<Comment> comments = commentRepository.findByScheduleId(schedule.getId());

        return comments.stream().map(CommentResponseDto::new).toList();
    }

    /**
     * 댓글 수정
     *
     * @param userId 댓글을 수정하려는 사용자 ID
     * @param commentId 수정할 댓글 ID
     * @param requestDto 수정 요청 DTO
     * @return 수정된 댓글의 응답 DTO
     */
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

    /**
     * 댓글 삭제
     *
     * @param commentId 삭제할 댓글 ID
     * @param userId 삭제를 요청한 사용자 ID
     */
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

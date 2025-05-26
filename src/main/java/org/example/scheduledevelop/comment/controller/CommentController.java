package org.example.scheduledevelop.comment.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.comment.dto.CommentCreateRequestDto;
import org.example.scheduledevelop.comment.dto.CommentCreateResponseDto;
import org.example.scheduledevelop.comment.dto.CommentResponseDto;
import org.example.scheduledevelop.comment.dto.CommentUpdateRequestDto;
import org.example.scheduledevelop.comment.service.CommentService;
import org.example.scheduledevelop.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     *
     * @param scheduleId 댓글 작성할 일정의 id
     * @param requestDto 댓글 생성 요청 데이터
     * @param session 현재 로그인한 사용자 정보를 담고 있는 세션
     * @return 생성된 댓글 정보
     */
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentCreateResponseDto> createComment(@PathVariable Long scheduleId, @RequestBody @Valid CommentCreateRequestDto requestDto, HttpSession session){
        User user = (User) session.getAttribute("user");

        CommentCreateResponseDto comment = commentService.createComment(user.getId(), scheduleId, requestDto);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    /**
     * 일정에 등록된 모든 댓글을 조회
     *
     * @param scheduleId 조회할 일정 ID
     * @return 해당 일정의 댓글 리스트
     */
    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByScheduleId(@PathVariable Long scheduleId){
        List<CommentResponseDto> commentsByScheduleId = commentService.getCommentsByScheduleId(scheduleId);

        return new ResponseEntity<>(commentsByScheduleId, HttpStatus.OK);
    }

    /**
     * 댓글 수정
     *
     * @param commentId 수정할 댓글 ID
     * @param requestDto 수정할 내용이 담긴 요청 객체
     * @param session 현재 로그인한 사용자 정보를 담고 있는 세션
     * @return 수정된 댓글 정보
     */
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentUpdateRequestDto requestDto, HttpSession session){
        User user = (User) session.getAttribute("user");

        CommentResponseDto updateComment = commentService.updateComment(user.getId(), commentId, requestDto);

        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     *
     * @param commentId 삭제할 댓글 ID
     * @param session 현재 로그인한 사용자 정보를 담고 있는 세션
     * @return 삭제 성공 여부
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, HttpSession session){
        User user = (User) session.getAttribute("user");

        commentService.deleteComment(commentId, user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

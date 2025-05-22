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
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{scheduleId}")
    public ResponseEntity<CommentCreateResponseDto> createComment(@PathVariable Long scheduleId, @RequestBody @Valid CommentCreateRequestDto requestDto, HttpSession session){

        User user = (User) session.getAttribute("user");
        CommentCreateResponseDto comment = commentService.createComment(user.getId(), scheduleId, requestDto);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByScheduleId(@PathVariable Long scheduleId){
        List<CommentResponseDto> commentsByScheduleId = commentService.getCommentsByScheduleId(scheduleId);

        return new ResponseEntity<>(commentsByScheduleId, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentUpdateRequestDto requestDto, HttpSession session){
        User user = (User) session.getAttribute("user");

        CommentResponseDto updateComment = commentService.updateComment(user.getId(), commentId, requestDto);

        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }
}

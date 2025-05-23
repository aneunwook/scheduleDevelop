package org.example.scheduledevelop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EmailOrPasswordDoesNotMatch.class)
    public ResponseEntity<ErrorResponse> handlePasswordDoesNotMatch(EmailOrPasswordDoesNotMatch e){
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScheduleNotFound(ScheduleNotFoundException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFound(CommentNotFoundException e){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

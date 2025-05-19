package org.example.scheduledevelop.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.userdto.SignupRequestDto;
import org.example.scheduledevelop.dto.userdto.SignupResponseDto;
import org.example.scheduledevelop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> createUser(@RequestBody SignupRequestDto requestDto){
        SignupResponseDto user = userService.createUser(requestDto.getEmail(), requestDto.getUsername());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}

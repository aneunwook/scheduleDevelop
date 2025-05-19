package org.example.scheduledevelop.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.userdto.SignupRequestDto;
import org.example.scheduledevelop.dto.userdto.SignupResponseDto;
import org.example.scheduledevelop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<SignupResponseDto>> findAllUsers(){
        List<SignupResponseDto> allUser = userService.findAllUsers();

        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SignupResponseDto> findById(@PathVariable Long id){
        SignupResponseDto byId = userService.findById(id);

        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

}

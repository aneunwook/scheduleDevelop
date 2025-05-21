package org.example.scheduledevelop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.userdto.LoginRequestDto;
import org.example.scheduledevelop.dto.userdto.SignupRequestDto;
import org.example.scheduledevelop.dto.userdto.SignupResponseDto;
import org.example.scheduledevelop.dto.userdto.UpdateUserPasswordDto;
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
    public ResponseEntity<SignupResponseDto> createUser(@RequestBody @Valid SignupRequestDto requestDto){
        SignupResponseDto user = userService.createUser(requestDto.getEmail(), requestDto.getUsername(), requestDto.getPassword());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletRequest request){
        userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword(), request);

        return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
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

    @PutMapping("/{id}")
    public ResponseEntity<SignupResponseDto> updatePassword(@PathVariable Long id, @RequestBody @Valid UpdateUserPasswordDto requestDto){
        SignupResponseDto updatePassword = userService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(updatePassword, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

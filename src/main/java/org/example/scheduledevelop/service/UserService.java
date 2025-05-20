package org.example.scheduledevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.userdto.SignupResponseDto;
import org.example.scheduledevelop.entity.User;
import org.example.scheduledevelop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignupResponseDto createUser(String email, String username, String password) {
        User user = new User(email, username, password);

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser);
    }

    public List<SignupResponseDto> findAllUsers() {
        return userRepository.findAll().stream().map(SignupResponseDto::new).toList();
    }

    public SignupResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다"));

        return new SignupResponseDto(user);
    }

    public SignupResponseDto updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다"));

        if(!user.getPassword().equals(oldPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(newPassword);

        return new SignupResponseDto(user);
    }
}

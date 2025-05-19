package org.example.scheduledevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.userdto.SignupResponseDto;
import org.example.scheduledevelop.entity.User;
import org.example.scheduledevelop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignupResponseDto createUser(String email, String username) {
        User user = new User(email, username);

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
}

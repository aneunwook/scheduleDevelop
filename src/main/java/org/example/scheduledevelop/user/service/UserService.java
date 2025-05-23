package org.example.scheduledevelop.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.config.PasswordEncoder;
import org.example.scheduledevelop.exception.ForbiddenException;
import org.example.scheduledevelop.exception.EmailOrPasswordDoesNotMatch;
import org.example.scheduledevelop.exception.UserNotFoundException;
import org.example.scheduledevelop.user.dto.SignupResponseDto;
import org.example.scheduledevelop.user.entity.User;
import org.example.scheduledevelop.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDto createUser(String email, String username, String password) {
        String encode = passwordEncoder.encode(password);

        User user = new User(email, username, encode);

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailOrPasswordDoesNotMatch("이메일이 일치하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new EmailOrPasswordDoesNotMatch("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }
    }

    public List<SignupResponseDto> findAllUsers() {
        return userRepository.findAll().stream().map(SignupResponseDto::new).toList();
    }

    public SignupResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));

        return new SignupResponseDto(user);
    }

    @Transactional
    public SignupResponseDto updatePassword(Long id, String oldPassword, String newPassword, Long userId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));

        if(!userId.equals(user.getId())){
            throw new ForbiddenException("본인만 수정할 수 있습니다.");
        }

        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new EmailOrPasswordDoesNotMatch("비밀번호가 일치하지 않습니다.");
        }

        String encode = passwordEncoder.encode(newPassword);

        user.setPassword(encode);

        return new SignupResponseDto(user);
    }

    public void deleteUser(Long id, Long userId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));

        if(!user.getId().equals(userId)){
            throw new ForbiddenException("본인만 삭제할 수 있습니다.");
        }

        userRepository.delete(user);
    }


}

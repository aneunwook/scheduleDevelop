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

    /**
     *  사용자 생성
     *
     * @param email 이메일
     * @param username 이름
     * @param password 비밀번호
     * @return 생성된 사용자 정보
     */
    public SignupResponseDto createUser(String email, String username, String password) {
        String encode = passwordEncoder.encode(password);

        User user = new User(email, username, encode);

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser);
    }

    /**
     * 로그인
     *
     * @param email 사용자의 이메일
     * @param password 사용자의 비밀번호
     * @return 인증된 사용자 객체
     */
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EmailOrPasswordDoesNotMatch("이메일이 일치하지 않습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new EmailOrPasswordDoesNotMatch("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    /**
     * 로그아웃
     *
     * @param request HTTP 요청 객체
     */
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }
    }

    /**
     * 전체 사용자 조회
     *
     * @return 사용자 DTO 리스트
     */
    public List<SignupResponseDto> findAllUsers() {
        return userRepository.findAll().stream().map(SignupResponseDto::new).toList();
    }

    /**
     * ID로 사용자 조회
     *
     * @param id 사용자 ID
     * @return 사용자 DTO
     */
    public SignupResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));

        return new SignupResponseDto(user);
    }

    /**
     * 비밀번호 수정
     *
     * @param id 사용자 ID
     * @param oldPassword 기존 비밀번호
     * @param newPassword 새 비밀번호
     * @param userId 현재 로그인한 사용자 ID
     * @return 수정된 사용자 정보
     */
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

    /**
     * 사용자 삭제
     *
     * @param id 삭제 대상 사용자 ID
     * @param userId 현재 로그인한 사용자 ID
     */
    public void deleteUser(Long id, Long userId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("해당 유저가 없습니다."));

        if(!user.getId().equals(userId)){
            throw new ForbiddenException("본인만 삭제할 수 있습니다.");
        }

        userRepository.delete(user);
    }


}

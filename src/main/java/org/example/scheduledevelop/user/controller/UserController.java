package org.example.scheduledevelop.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.user.dto.LoginRequestDto;
import org.example.scheduledevelop.user.dto.SignupRequestDto;
import org.example.scheduledevelop.user.dto.SignupResponseDto;
import org.example.scheduledevelop.user.dto.UpdateUserPasswordDto;
import org.example.scheduledevelop.user.entity.User;
import org.example.scheduledevelop.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저 생성 요청(회원가입)
     *
     * @param requestDto 회원가입 요청 데이터
     * @return 생성된 사용자 정보
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> createUser(@RequestBody @Valid SignupRequestDto requestDto){
        SignupResponseDto user = userService.createUser(requestDto.getEmail(), requestDto.getUsername(), requestDto.getPassword());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * 로그인 요청
     *
     * @param loginRequestDto 로그인 요청 데이터
     * @param request HTTP 요청 객체 (세션 저장용)
     * @return 로그인 성공 메시지
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletRequest request){

        User user = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
    }

    /**
     * 로그아웃 요청
     *
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체 (쿠키 삭제용)
     * @return 로그아웃 메시지
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response){
        userService.logout(request);

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ResponseEntity<>("로그아웃 됨", HttpStatus.OK);
    }

    /**
     * 전체 사용자 목록을 조회
     *
     * @return 모든 사용자 정보 리스트
     */
    @GetMapping
    public ResponseEntity<List<SignupResponseDto>> findAllUsers(){
        List<SignupResponseDto> allUser = userService.findAllUsers();

        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    /**
     * 특정 사용자를 조회
     *
     * @param id 사용자 ID
     * @return 해당 사용자의 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<SignupResponseDto> findById(@PathVariable Long id){
        SignupResponseDto byId = userService.findById(id);

        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    /**
     * 비밀번호를 수정
     *
     * @param id 수정 대상 사용자 ID
     * @param requestDto 이전/새 비밀번호 정보
     * @param session 현재 로그인한 사용자 세션
     * @return 수정된 사용자 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<SignupResponseDto> updatePassword(@PathVariable Long id, @RequestBody @Valid UpdateUserPasswordDto requestDto, HttpSession session){
        User user = (User) session.getAttribute("user");

        SignupResponseDto updatePassword = userService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword(), user.getId());

        return new ResponseEntity<>(updatePassword, HttpStatus.OK);
    }

    /**
     * 회원을 삭제
     *
     * @param id 삭제할 사용자 ID
     * @param session 현재 로그인한 사용자 세션
     * @return  HTTP 200 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpSession session){
        User user = (User) session.getAttribute("user");

        userService.deleteUser(id, user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

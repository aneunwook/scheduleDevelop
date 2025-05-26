package org.example.scheduledevelop.schedule.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.schedule.dto.ScheduleCreateRequestDto;
import org.example.scheduledevelop.schedule.dto.ScheduleCreateResponseDto;
import org.example.scheduledevelop.schedule.dto.ScheduleResponseDto;
import org.example.scheduledevelop.schedule.dto.UpdateScheduleRequestDto;
import org.example.scheduledevelop.user.entity.User;
import org.example.scheduledevelop.schedule.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 새로운 일정 등록
     *
     * @param requestDto 일정 생성 요청 데이터
     * @param session 로그인한 사용자 정부를 담고 있는 세션
     * @return 생성된 일정 응답 데이터
     */
    @PostMapping
    public ResponseEntity<ScheduleCreateResponseDto> save(@RequestBody @Valid ScheduleCreateRequestDto requestDto, HttpSession session){

        User user = (User) session.getAttribute("user");

        ScheduleCreateResponseDto saved = scheduleService.save(
                requestDto.getTitle(),
                requestDto.getContents(),
                user.getId());

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * 모든 일정을 수정된 날짜 기준으로 페이징하여 전체 조회
     *
     * @param page 조회할 페이지 번호 (기본값 : 0)
     * @param size 페이지당 항목 수 (기본값 : 10)
     * @return 페이징된 일정 응답 데이터
     */
    @GetMapping
    public ResponseEntity<Page<ScheduleResponseDto>> findAllSortedByUpdatedAt(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size){
        Page<ScheduleResponseDto> findAll = scheduleService.findAllSortedByUpdatedAt(page, size);

        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    /**
     * 특정 일정 id 값으로 일정 조회
     *
     * @param id 조회할 일정의 id
     * @return 조회된 일정 응답 데이터
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id){
        ScheduleResponseDto findId = scheduleService.findById(id);

        return new ResponseEntity<>(findId, HttpStatus.OK);
    }

    /**
     * 특정 일정을 id로 일정 수정
     *
     * @param id 수정할 일정의 id
     * @param requestDto 일정 수정 요청 데이터
     * @param session 로그인된 사용자 정보를 담고 있는 세션
     * @return 수정된 일정 응답 데이터
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody @Valid UpdateScheduleRequestDto requestDto, HttpSession session){
        User user = (User) session.getAttribute("user");

        ScheduleResponseDto updated = scheduleService.updateSchedule(id, requestDto.getTitle(), requestDto.getContents(), user.getId());

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    /**
     * 특정 일정을 id로 일정 삭제
     *
     * @param id 삭제할 일정의 id
     * @param session 로그인된 사용자 정보를 담고 있는 세션
     * @return 삭제된 일정 응답 데이터
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpSession session){

        User user = (User) session.getAttribute("user");

        scheduleService.deleteSchedule(id, user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package org.example.scheduledevelop.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.comment.entity.Comment;
import org.example.scheduledevelop.comment.repository.CommentRepository;
import org.example.scheduledevelop.exception.ForbiddenException;
import org.example.scheduledevelop.exception.ScheduleNotFoundException;
import org.example.scheduledevelop.exception.UserNotFoundException;
import org.example.scheduledevelop.schedule.dto.ScheduleCreateResponseDto;
import org.example.scheduledevelop.schedule.dto.ScheduleResponseDto;
import org.example.scheduledevelop.schedule.entity.Schedule;
import org.example.scheduledevelop.user.entity.User;
import org.example.scheduledevelop.schedule.repository.ScheduleRepository;
import org.example.scheduledevelop.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    /**
     * 일정 생성
     *
     * @param title 일정 제목
     * @param contents 일정 내용
     * @param userId 작성자 ID (세션)
     * @return 생성된 일정 DTO
     */
    public ScheduleCreateResponseDto save(String title, String contents, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다"));

        Schedule schedule = new Schedule(title, contents, user);

        Schedule saved = scheduleRepository.save(schedule);

        return new ScheduleCreateResponseDto(saved);

    }

    /**
     * 수정일 기준으로 페이징 하며 일정 전체 조회
     *
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 일정 목록
     */
    public Page<ScheduleResponseDto> findAllSortedByUpdatedAt(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return scheduleRepository.findAllByOrderByUpdatedAtDesc(pageable).map(ScheduleResponseDto::toDto);
    }

    /**
     * 특정 일정을 id로 일정 조회
     *
     * @param id 일정의 id
     * @return 조회된 일정 DTO
     */
    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("해당 일정을 찾을 수 없습니다"));

        return ScheduleResponseDto.toDto(schedule);

    }

    /**
     *  특정 일정을 id로 일정 수정
     *
     * @param id 일정의 id
     * @param title 일정 수정할 제목
     * @param contents 일정 수정할 내용
     * @param userId 사용자 ID
     * @return 수정된 일정 DTO
     */
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String title, String contents, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다"));

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("해당 일정을 찾을 수 없습니다"));

        if (!user.getId().equals(schedule.getUser().getId())){
            throw new ForbiddenException("해당 게시물을 수정 할 수 없습니다.");
        }

        schedule.setTitle(title);
        schedule.setContents(contents);

        return ScheduleResponseDto.toDto(schedule);
    }

    /**
     * 특정 일정을 id로 일정 삭제
     *
     * @param id 일정 ID
     * @param userId 요청한 사용자 ID
     */
    @Transactional
    public void deleteSchedule(Long id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다"));

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("해당 일정을 찾을 수 없습니다"));

        if (!user.getId().equals(schedule.getUser().getId())){
            throw new ForbiddenException("해당 게시물을 삭제 할 수 없습니다.");
        }

        scheduleRepository.delete(schedule);

    }
}

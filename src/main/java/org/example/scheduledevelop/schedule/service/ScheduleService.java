package org.example.scheduledevelop.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.comment.entity.Comment;
import org.example.scheduledevelop.comment.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    public ScheduleCreateResponseDto save(String title, String contents, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        Schedule schedule = new Schedule(title, contents, user);

        Schedule saved = scheduleRepository.save(schedule);

        return new ScheduleCreateResponseDto(saved);

    }

    public Page<ScheduleResponseDto> findAllSortedByUpdatedAt(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scheduleRepository.findAllByOrderByUpdatedAtDesc(pageable).map(ScheduleResponseDto::toDto);
    }

    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일정을 찾을 수 없습니다"));

        return ScheduleResponseDto.toDto(schedule);

    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String title, String contents, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일정을 찾을 수 없습니다"));

        if (!user.getId().equals(schedule.getUser().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 게시물을 수정 할 수 없습니다.");
        }

        schedule.setTitle(title);
        schedule.setContents(contents);

        return ScheduleResponseDto.toDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일정을 찾을 수 없습니다"));

        if (!user.getId().equals(schedule.getUser().getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 게시물을 삭제 할 수 없습니다.");
        }

        scheduleRepository.delete(schedule);

    }
}

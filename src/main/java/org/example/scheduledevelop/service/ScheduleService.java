package org.example.scheduledevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.scheduledto.CreateResponseDto;
import org.example.scheduledevelop.dto.scheduledto.ScheduleResponseDto;
import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public CreateResponseDto save(String username, String title, String contents) {
        Schedule schedule = new Schedule(username, title, contents);

        Schedule saved = scheduleRepository.save(schedule);

        return new CreateResponseDto(saved.getId(), saved.getUsername(), saved.getTitle(), saved.getContents());

    }

    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
    }

    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일정을 찾을 수 없습니다"));

        return ScheduleResponseDto.toDto(schedule);

    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, String title, String contents) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 일정을 찾을 수 없습니다"));

        schedule.setTitle(title);
        schedule.setContents(contents);

        return ScheduleResponseDto.toDto(schedule);
    }

}

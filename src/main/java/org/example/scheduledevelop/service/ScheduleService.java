package org.example.scheduledevelop.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.scheduledto.CreateResponseDto;
import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public CreateResponseDto save(String username, String title, String contents) {
        Schedule schedule = new Schedule(username, title, contents);

        Schedule saved = scheduleRepository.save(schedule);

        return new CreateResponseDto(saved.getId(), saved.getUsername(), saved.getTitle(), saved.getContents());

    }
}

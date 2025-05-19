package org.example.scheduledevelop.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.scheduledto.CreateRequestDto;
import org.example.scheduledevelop.dto.scheduledto.CreateResponseDto;
import org.example.scheduledevelop.entity.Schedule;
import org.example.scheduledevelop.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateResponseDto> save(@RequestBody CreateRequestDto requestDto){
        CreateResponseDto saved = scheduleService.save(requestDto.getUsername(),
                requestDto.getTitle(),
                requestDto.getContents());

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}

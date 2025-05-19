package org.example.scheduledevelop.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduledevelop.dto.scheduledto.CreateRequestDto;
import org.example.scheduledevelop.dto.scheduledto.CreateResponseDto;
import org.example.scheduledevelop.dto.scheduledto.ScheduleResponseDto;
import org.example.scheduledevelop.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll(){
        List<ScheduleResponseDto> findAll = scheduleService.findAll();

        return new ResponseEntity<>(findAll, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id){
        ScheduleResponseDto findId = scheduleService.findById(id);

        return new ResponseEntity<>(findId, HttpStatus.OK);
    }

}

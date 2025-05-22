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
    public ResponseEntity<ScheduleCreateResponseDto> save(@RequestBody @Valid ScheduleCreateRequestDto requestDto, HttpSession session){

        User user = (User) session.getAttribute("user");

        ScheduleCreateResponseDto saved = scheduleService.save(
                requestDto.getTitle(),
                requestDto.getContents(),
                user.getId());

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

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody @Valid UpdateScheduleRequestDto requestDto, HttpSession session){
        User user = (User) session.getAttribute("user");

        ScheduleResponseDto updated = scheduleService.updateSchedule(id, requestDto.getTitle(), requestDto.getContents(), user.getId());

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpSession session){

        User user = (User) session.getAttribute("user");

        scheduleService.deleteSchedule(id, user.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

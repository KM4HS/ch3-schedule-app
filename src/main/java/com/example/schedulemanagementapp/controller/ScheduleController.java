package com.example.schedulemanagementapp.controller;

import com.example.schedulemanagementapp.dto.ScheduleRequestDto;
import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.controller
 * <li>fileName       : ScheduleController
 * <li>author         : daca0
 * <li>date           : 24. 11. 5.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 5.        daca0       최초 생성
 * </p>
 */

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 할일 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto dto
    ) {

        return new ResponseEntity<>(scheduleService.createSchedule(dto.getPassword(), dto.getContents(), dto.getWriter()), HttpStatus.CREATED);
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long id
    ) {

        return new ResponseEntity<>(scheduleService.findScheduleByIdOrElseThrow(id), HttpStatus.OK);
    }

    // 다중 조회 (조건있음)
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedulesByCond(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String writer
    ) {
        System.out.println(date +" "+ writer);

        return new ResponseEntity<>(scheduleService.findAllScheduleByCond(date, writer), HttpStatus.OK);
    }

    // 할일 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {

        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getPassword(), dto.getContents(), dto.getWriter()), HttpStatus.OK);
    }

    // 단일 할일 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteScheduleById(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {

        scheduleService.deleteSchedule(id, dto.getPassword());
        return new ResponseEntity<>("complete", HttpStatus.OK);
    }
}

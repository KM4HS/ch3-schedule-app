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
 * <li>description    : 일정 관련 실행을 관리하는 controller 클래스
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

    /**
     * 일정 생성
     *
     * @param dto 일정 요청값을 받음
     * @return 생성된 일정을 반환
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto dto
    ) {

        return new ResponseEntity<>(scheduleService.createSchedule(dto.getPassword(), dto.getContents(), dto.getUserId()), HttpStatus.CREATED);
    }

    /**
     * 일정 단건 조회
     *
     * @param id 일정 식별자
     * @return 식별한 일정을 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long id
    ) {

        return new ResponseEntity<>(scheduleService.findScheduleByIdOrElseThrow(id), HttpStatus.OK);
    }

    /**
     * 조건별 다건 조회
     *
     * @param date 날짜 조건
     * @param user 작성자 조건
     * @return 조건에 맞는 일정 검색 결과를 배열 형태로 반환. 조건은 필수가 아님.
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedulesByCond(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String user
    ) {

        return new ResponseEntity<>(scheduleService.findAllScheduleByCond(date, user), HttpStatus.OK);
    }

    /**
     * 전체 schedule을 페이지 단위로 조회
     *
     * @param pageIndex 페이지 번호
     * @param pageSize  페이지 크기
     * @return 페이징 단위의 일정 리스트 반환
     */
    @GetMapping("/pages")
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedulesInPage(
            @RequestParam int pageIndex,
            @RequestParam int pageSize
    ) {

        return new ResponseEntity<>(scheduleService.findAllSchedulesInPage(pageIndex, pageSize), HttpStatus.OK);
    }

    /**
     * 일정 수정
     *
     * @param id  일정 식별자
     * @param dto 일정 요청값
     * @return 수정된 일정을 반환
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {

        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getPassword(), dto.getContents()), HttpStatus.OK);
    }

    /**
     * 일정 삭제
     *
     * @param id  일정 식별자
     * @param dto 비밀번호를 담은 일정 정보
     * @return 삭제되었다는 메시지 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteScheduleById(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {

        scheduleService.deleteSchedule(id, dto.getPassword());
        return new ResponseEntity<>("complete", HttpStatus.OK);
    }
}

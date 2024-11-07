package com.example.schedulemanagementapp.service;

import com.example.schedulemanagementapp.dto.ScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.service
 * <li>fileName       : ScheduleService
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    : 일정 서비스 레이어 인터페이스
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */
public interface ScheduleService {

    // 일정 생성
    ScheduleResponseDto createSchedule(String password, String contents, Long userId);

    // 단건 조회
    ScheduleResponseDto findScheduleByIdOrElseThrow(Long id);

    // 조건별 일정 전체 조회
    List<ScheduleResponseDto> findAllScheduleByCond(LocalDate date, String user);

    // 일정 수정
    ScheduleResponseDto updateSchedule(Long id, String password, String contents, String user);

    // 일정 삭제
    void deleteSchedule(Long id, String password);
}

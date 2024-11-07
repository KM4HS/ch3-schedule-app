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
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */
public interface ScheduleService {
    ScheduleResponseDto createSchedule (String password, String contents, String writer);
    ScheduleResponseDto findScheduleById (Long id);
    List<ScheduleResponseDto> findAllScheduleByCondition (LocalDate date, String writer);
    ScheduleResponseDto updateSchedule (Long id, String password, String contents);
    void deleteSchedule (Long id, String password);
}

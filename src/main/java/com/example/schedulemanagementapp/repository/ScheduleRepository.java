package com.example.schedulemanagementapp.repository;

import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.repository
 * <li>fileName       : ScheduleRepository
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */
public interface ScheduleRepository {
    ScheduleResponseDto createSchedule (Schedule schedule);
    Schedule findScheduleByIdOrElseThrow (Long id);
    List<ScheduleResponseDto> findAllScheduleByCond (LocalDate date, String writer);
    int updateSchedule (Long id, String contents);
    void deleteSchedule (Long id);

}

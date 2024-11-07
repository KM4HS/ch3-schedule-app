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
 * <li>description    : 일정 repository 레이어 인터페이스
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */
public interface ScheduleRepository {
    // 일정 생성
    ScheduleResponseDto createSchedule(Schedule schedule);

    // 일정 단건 조회, 없을시 throw
    Schedule findScheduleByIdOrElseThrow(Long id);

    // 조건별 일정 전체 조회
    List<ScheduleResponseDto> findAllScheduleByCond(LocalDate date, String user);

    // 일정 수정
    int updateSchedule(Long id, String contents, String user);

    // 일정 삭제
    void deleteSchedule(Long id);

}

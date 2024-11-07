package com.example.schedulemanagementapp.dto;

import com.example.schedulemanagementapp.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.dto
 * <li>fileName       : ScheduleResponseDto
 * <li>author         : daca0
 * <li>date           : 24. 11. 5.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 5.        daca0       최초 생성
 * </p>
 */
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private final Long id;
    private final LocalDate date;
    private final String contents;
    private final String writer;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.date = schedule.getDate();
        this.contents = schedule.getContents();
        this.writer = schedule.getWriter();
    }
}

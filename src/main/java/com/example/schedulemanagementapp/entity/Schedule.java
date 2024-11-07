package com.example.schedulemanagementapp.entity;

import com.example.schedulemanagementapp.dto.ScheduleRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.entity
 * <li>fileName       : Schedule
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
public class Schedule {
    private Long id;
    private String password;
    private LocalDate date;
    private String contents;
    private String writer;

    public Schedule (String password, String contents, String writer) {
        this.password = password;
        this.contents = contents;
        this.writer = writer;
    }
}

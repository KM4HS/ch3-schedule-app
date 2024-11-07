package com.example.schedulemanagementapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.entity
 * <li>fileName       : Schedule
 * <li>author         : daca0
 * <li>date           : 24. 11. 5.
 * <li>description    : schedule db와 연동되는 엔티티
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
    private Long userId;

    public Schedule(String password, String contents, Long userId) {
        this.password = password;
        this.contents = contents;
        this.userId = userId;
    }
}

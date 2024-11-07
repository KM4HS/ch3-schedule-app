package com.example.schedulemanagementapp.dto;

import lombok.Getter;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.dto
 * <li>fileName       : ScheduleRequestDto
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
public class ScheduleRequestDto {
    private String password;
    private String title;
    private String contents;
    private String writer;
}

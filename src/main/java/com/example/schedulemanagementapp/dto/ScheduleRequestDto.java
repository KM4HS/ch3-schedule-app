package com.example.schedulemanagementapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.dto
 * <li>fileName       : ScheduleRequestDto
 * <li>author         : daca0
 * <li>date           : 24. 11. 5.
 * <li>description    : 일정 요청 dto
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 5.        daca0       최초 생성
 * </p>
 */

@Getter
public class ScheduleRequestDto {

    @NotEmpty
    private String password;

    @Size(min = 1, max = 200)
    private String contents;

    @NotNull
    private Long userId;
}

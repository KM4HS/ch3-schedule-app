package com.example.schedulemanagementapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.exceptions
 * <li>fileName       : ExceptionResponseDto
 * <li>author         : daca0
 * <li>date           : 24. 11. 8.
 * <li>description    : 예외 응답을 위한 dto
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 8.        daca0       최초 생성
 * </p>
 */

@Getter
@RequiredArgsConstructor
public class ExceptionResponseDto {
    private final String code;
    private final int status;
    private final String msg;
}

package com.example.schedulemanagementapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.exceptions
 * <li>fileName       : ExceptionResponseDto
 * <li>author         : daca0
 * <li>date           : 24. 11. 8.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 8.        daca0       최초 생성
 * </p>
 */

@Getter
@AllArgsConstructor
public class ExceptionResponseDto {
    private String code;
    private int status;
    private String msg;
}

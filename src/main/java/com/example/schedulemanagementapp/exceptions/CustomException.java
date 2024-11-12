package com.example.schedulemanagementapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.exceptions
 * <li>fileName       : CustomException
 * <li>author         : daca0
 * <li>date           : 24. 11. 8.
 * <li>description    : 커스텀 예외
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 8.        daca0       최초 생성
 * </p>
 */
@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    final ExceptionCode exceptionCode;
}

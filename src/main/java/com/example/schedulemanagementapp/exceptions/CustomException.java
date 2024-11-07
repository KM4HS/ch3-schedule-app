package com.example.schedulemanagementapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.exceptions
 * <li>fileName       : CustomException
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
public class CustomException extends RuntimeException{
    final ExceptionCode exceptionCode;
}

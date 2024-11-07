package com.example.schedulemanagementapp.exceptions;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.exceptions
 * <li>fileName       : GlobalExceptionHandler
 * <li>author         : daca0
 * <li>date           : 24. 11. 8.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 8.        daca0       최초 생성
 * </p>
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseDto> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.exceptionCode.getHttpStatus())
                .body(new ExceptionResponseDto(
                        e.exceptionCode.name(),
                        e.exceptionCode.getHttpStatus().value(),
                        e.exceptionCode.getMessage()
                ));
    }
}

package com.example.schedulemanagementapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.exceptions
 * <li>fileName       : GlobalExceptionHandler
 * <li>author         : daca0
 * <li>date           : 24. 11. 8.
 * <li>description    : 전역 예외 처리 클래스
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 8.        daca0       최초 생성
 * </p>
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비밀번호 불일치 등, {@link ExceptionCode}의 커스텀 예외를 처리
     *
     * @param e {@link CustomException}
     * @return 예외 dto
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseDto> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.exceptionCode.getHttpStatus())
                .body(new ExceptionResponseDto(
                        e.exceptionCode.name(),
                        e.exceptionCode.getHttpStatus().value(),
                        e.exceptionCode.getMessage()
                ));
    }

    /**
     * 요청값 유효성 검증 예외를 처리
     *
     * @param e {@link MethodArgumentNotValidException}
     * @return 예외 dto
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleValidException(MethodArgumentNotValidException e) {

        return new ResponseEntity<>(new ExceptionResponseDto(
                e.getFieldError().getField(),
                e.getStatusCode().value(),
                e.getFieldError().getDefaultMessage()
        ), HttpStatus.BAD_REQUEST);
    }
}

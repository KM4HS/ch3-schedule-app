package com.example.schedulemanagementapp.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.exceptions
 * <li>fileName       : ExceptionCode
 * <li>author         : daca0
 * <li>date           : 24. 11. 8.
 * <li>description    : 커스텀 예외 목록을 저장하는 enum
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 8.        daca0       최초 생성
 * </p>
 */

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INVALID_REQUEST(HttpStatus.NOT_FOUND, "잘못된 조회 요청입니다."),
    DELETED_RESOURCE(HttpStatus.NOT_FOUND, "삭제된 일정입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

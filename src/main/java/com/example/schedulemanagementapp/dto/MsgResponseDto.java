package com.example.schedulemanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.dto
 * <li>fileName       : MsgResponseDto
 * <li>author         : daca0
 * <li>date           : 24. 11. 8.
 * <li>description    : 메시지를 포함하여 응답하는 dto
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 8.        daca0       최초 생성
 * </p>
 */

@Getter
@AllArgsConstructor
public class MsgResponseDto {
    private final String msg;
    private final Object contains;

    public MsgResponseDto(String msg) {
        this.msg = msg;
        this.contains = "none";
    }
}

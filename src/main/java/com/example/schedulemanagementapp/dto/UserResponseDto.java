package com.example.schedulemanagementapp.dto;

import com.example.schedulemanagementapp.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.dto
 * <li>fileName       : UserResponseDto
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    : 사용자 정보 응답 dto
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */

@Getter
@RequiredArgsConstructor
public class UserResponseDto {
    private final Long id;
    private final LocalDate regDate;
    private final LocalDate modDate;
    private final String email;
    private final String name;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.regDate = user.getRegDate();
        this.modDate = user.getModDate();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}

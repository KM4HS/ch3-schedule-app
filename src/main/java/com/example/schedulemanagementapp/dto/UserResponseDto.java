package com.example.schedulemanagementapp.dto;

import com.example.schedulemanagementapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.dto
 * <li>fileName       : UserResponseDto
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private LocalDate regDate;
    private LocalDate modDate;
    private String email;
    private String name;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.regDate = user.getRegDate();
        this.modDate = user.getModDate();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}

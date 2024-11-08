package com.example.schedulemanagementapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.dto
 * <li>fileName       : UserRequestDto
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    : 사용자 정보 요청 dto
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */

@Getter
public class UserRequestDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Email
    private String email;
}

package com.example.schedulemanagementapp.service;

import com.example.schedulemanagementapp.dto.UserRequestDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.service
 * <li>fileName       : UserService
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */
public interface UserService {

    // 유저 등록
    UserResponseDto createUser(String email, String name);

    // 유저 단일 조회
    UserResponseDto findUserByIdOrElseThrow(Long id);

    // 유저 정보 수정
    UserResponseDto updateuser(Long id, String email, String name);

}

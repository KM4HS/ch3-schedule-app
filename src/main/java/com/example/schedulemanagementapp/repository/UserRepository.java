package com.example.schedulemanagementapp.repository;

import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.entity.User;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.repository
 * <li>fileName       : UserRepository
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    : 유저 repository 레이어 인터페이스
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */
public interface UserRepository {

    // 유저 생성
    UserResponseDto createUser(User user);

    // 유저 단일 조회
    User findUserByIdOrElseThrow(Long id);

    String findNameByUserIdOrElseThrow(Long id);

    // 유저 정보 수정
    int updateUser(Long id, String name);
}

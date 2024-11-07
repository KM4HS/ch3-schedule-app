package com.example.schedulemanagementapp.service;

import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.entity.User;
import com.example.schedulemanagementapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.service
 * <li>fileName       : UserServiceImpl
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    : {@link UserService} 구현체
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 유저 등록
     * @param email 등록할 유저 email
     * @param name 등록할 유저명
     * @return 등록된 유저 정보 dto
     */
    @Override
    public UserResponseDto createUser(String email, String name) {

        return userRepository.createUser(new User(email, name));
    }

    /**
     * 유저 조회
     * @param id 유저 식별자
     * @return 조회된 유저 정보
     */
    @Override
    public UserResponseDto findUserByIdOrElseThrow(Long id) {

        return new UserResponseDto(userRepository.findUserByIdOrElseThrow(id));
    }

    /**
     * 유저 이름 수정
     * @param id 유저 식별자
     * @param name 수정할 유저명
     * @return 수정된 유저 정보
     */
    @Transactional
    @Override
    public UserResponseDto updateUser(Long id, String name) {

        // 이름이 null인 경우 400
        if (name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }

        // 수정된 user가 없을 경우 404
        if (userRepository.updateUser(id, name) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new UserResponseDto(userRepository.findUserByIdOrElseThrow(id));
    }
}

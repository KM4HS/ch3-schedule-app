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
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto createUser(String email, String name) {

        return userRepository.createUser(new User(email, name));
    }

    @Override
    public UserResponseDto findUserByIdOrElseThrow(Long id) {

        return new UserResponseDto(userRepository.findUserByIdOrElseThrow(id));
    }

    @Transactional
    @Override
    public UserResponseDto updateuser(Long id, String email, String name) {

        if(email == null || name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and name are required");
        }

        if(userRepository.updateUser(id, email, name) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = "+id);
        }

        return new UserResponseDto(userRepository.findUserByIdOrElseThrow(id));
    }
}

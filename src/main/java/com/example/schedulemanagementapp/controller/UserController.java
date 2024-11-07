package com.example.schedulemanagementapp.controller;

import com.example.schedulemanagementapp.dto.UserRequestDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.controller
 * <li>fileName       : UserController
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 유저 등록
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @RequestBody UserRequestDto dto
    ) {

        return new ResponseEntity<>(userService.createUser(dto.getEmail(), dto.getName()), HttpStatus.CREATED);
    }

    // 유저 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserByIdOrElseThrow(
            @PathVariable Long id
    ) {

        return new ResponseEntity<>(userService.findUserByIdOrElseThrow(id), HttpStatus.OK);
    }

    // 유저 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto dto
    ) {

        return new ResponseEntity<>(userService.updateuser(id, dto.getEmail(), dto.getName()), HttpStatus.OK);
    }

}

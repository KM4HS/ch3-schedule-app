package com.example.schedulemanagementapp.controller;

import com.example.schedulemanagementapp.dto.UserRequestDto;
import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.controller
 * <li>fileName       : UserController
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    : User의 등록, 수정, 조회의 실행을 관리하는 Controller 클래스
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

    /**
     * 유저 등록
     *
     * @param dto 유저 등록을 위한 요청값 dto
     * @return 등록된 유저 정보
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto dto
    ) {

        return new ResponseEntity<>(userService.createUser(dto.getEmail(), dto.getName()), HttpStatus.CREATED);
    }

    /**
     * 유저 조회
     *
     * @param id 유저 식별자
     * @return 조회된 유저 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserByIdOrElseThrow(
            @PathVariable Long id
    ) {

        return new ResponseEntity<>(userService.findUserByIdOrElseThrow(id), HttpStatus.OK);
    }

    /**
     * 유저 정보 수정
     *
     * @param id  유저 식별자
     * @param dto 요청된 수정사항을 담은 dto
     * @return 수정된 유저 정보
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto dto
    ) {

        return new ResponseEntity<>(userService.updateUser(id, dto.getName()), HttpStatus.OK);
    }

}

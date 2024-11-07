package com.example.schedulemanagementapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.entity
 * <li>fileName       : User
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
public class User {
    private Long id;
    private String email;
    private String name;
    private LocalDate regDate;
    private LocalDate modDate;

    public User (String email, String name) {
        this.email = email;
        this.name = name;
    }
}

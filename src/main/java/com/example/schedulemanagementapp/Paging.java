package com.example.schedulemanagementapp;

import lombok.Getter;
import lombok.Setter;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp
 * <li>fileName       : Paging
 * <li>author         : daca0
 * <li>date           : 24. 11. 8.
 * <li>description    : schedule 검색 페이징 객체
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 8.        daca0       최초 생성
 * </p>
 */

@Getter
public class Paging {
    private final int pageIndex;
    private final int pageSize;
    private final int startRow;

    @Setter
    Long totalSize;

    public Paging(int pageIndex, int pageSize) {
        this.pageIndex = Math.max(pageIndex, 1);
        this.pageSize = pageSize;
        this.startRow = (pageIndex - 1) * pageSize;
    }
}

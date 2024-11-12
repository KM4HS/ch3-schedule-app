package com.example.schedulemanagementapp.dto;

import com.example.schedulemanagementapp.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.dto
 * <li>fileName       : ScheduleResponseDto
 * <li>author         : daca0
 * <li>date           : 24. 11. 5.
 * <li>description    : 일정 응답 dto
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 5.        daca0       최초 생성
 * </p>
 */
@Getter
@RequiredArgsConstructor
public class ScheduleResponseDto {
    private final Long id;
    private final LocalDate date;
    private final String contents;
    private final Long userId;
    private String userName;

    public ScheduleResponseDto(Schedule schedule, String userName) {
        this.id = schedule.getId();
        this.date = schedule.getDate();
        this.contents = schedule.getContents();
        this.userId = schedule.getUserId();
        this.userName = userName;
    }

    /**
     * db의 값을 응답 dto로 매핑
     *
     * @return 매핑된 dto 배열
     */
    public static RowMapper<ScheduleResponseDto> rowMapperToDto() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getDate("mod_date").toLocalDate(),
                        rs.getString("contents"),
                        rs.getLong("fk_userid")
                );
            }
        };
    }
}

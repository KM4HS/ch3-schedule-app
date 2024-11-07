package com.example.schedulemanagementapp.repository;

import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>packageName    : com.example.schedulemanagementapp.repository
 * <li>fileName       : ScheduleRepositoryImpl
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    :
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto createSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        LocalDate modDate = LocalDate.now();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("password", schedule.getPassword());
        parameters.put("contents", schedule.getContents());
        parameters.put("writer", schedule.getWriter());
        parameters.put("mod_date", modDate);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(), modDate, schedule.getContents(), schedule.getWriter());
    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", rowMapperToSchedule(), id);
        return result.stream()
                .findAny()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id)
                );
    }

    @Override
    public List<ScheduleResponseDto> findAllScheduleByCond(LocalDate date, String writer) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM schedule");

        // 조건이 있을 시 WHERE 절 추가
        if(date !=  null || writer != null) {
            sql.append(" WHERE");
        }

        // date 조건이 있는 경우
        boolean andFlag = false;
        if(date != null) {
            sql.append(" mod_date = ").append("'").append(date).append("'");
            andFlag = true;
        }

        // writer 조건이 있는 경우
        if(writer != null) {
            // 앞서 date 조건이 있으면 AND 추가
            if(andFlag) {
                sql.append(" AND");
            }
            sql.append(" writer = ").append("\"").append(writer).append("\"");
        }
        // 수정일 기준으로 내림차순 정렬
        sql.append(" ORDER BY mod_date DESC");

        System.out.println(sql);

        return jdbcTemplate.query(sql.toString(), rowMapperToDto());
    }

    @Override
    public int updateSchedule(Long id, String contents, String writer) {

        /*StringBuilder sql = new StringBuilder();
        sql.append("UPDATE schedule SET");

        boolean andFlag = false;
        if(contents != null) {
            sql.append(" contents = ").append("\"").append(contents).append("\"");
            andFlag = true;
        }

        if(writer != null) {
            if(andFlag) {
                sql.append(",");
            }
            sql.append(" writer = ").append("\"").append(writer).append("\"");
        }

        sql.append(" WHERE id = ").append(id);

        return jdbcTemplate.update(sql.toString());*/

        LocalDate modDate = LocalDate.now();
        return jdbcTemplate.update("UPDATE schedule SET contents = ?, writer = ?, mod_date = ? WHERE id = ?", contents, writer, modDate, id);
    }

    @Override
    public void deleteSchedule(Long id) {
        jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> rowMapperToDto() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getDate("mod_date").toLocalDate(),
                        rs.getString("contents"),
                        rs.getString("writer")
                );
            }
        };
    }

    private RowMapper<Schedule> rowMapperToSchedule() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("password"),
                        rs.getDate("mod_date").toLocalDate(),
                        rs.getString("contents"),
                        rs.getString("writer")
                );
            }
        };
    }
}

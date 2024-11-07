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

    /**
     * 일정을 db에 기록. Auto_increment로 생성된 key값, 현재 시간과 매개변수로 받은 데이터를 schedule 엔티티에 저장.
     *
     * @param schedule
     * @return 생성한 일정을 담은 응답 dto
     */
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

    /**
     * 일정 단건 조회. 찾는 일정이 없을 경우 throw
     *
     * @param id 일정 식별자
     * @return 찾은 schedule 엔티티
     */
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", rowMapperToSchedule(), id);
        return result.stream()
                .findAny()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id)
                );
    }

    /**
     * 조건별 일정 다건 조회. 조건에 따라 sql문 생성 후 찾은 값을 dto 배열로 저장.
     *
     * @param date   날짜 조건
     * @param writer 작성자 조건
     * @return 조건에 부합하는 일정 응답 dto 배열
     */
    @Override
    public List<ScheduleResponseDto> findAllScheduleByCond(LocalDate date, String writer) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM schedule");

        // 조건이 있을 시 WHERE 절 추가
        if (date != null || writer != null) {
            sql.append(" WHERE");
        }

        // date 조건이 있는 경우
        boolean andFlag = false;
        if (date != null) {
            sql.append(" mod_date = ").append("'").append(date).append("'");
            andFlag = true;
        }

        // writer 조건이 있는 경우
        if (writer != null) {
            // 앞서 date 조건이 있으면 AND 추가
            if (andFlag) {
                sql.append(" AND");
            }
            sql.append(" writer = ").append("\"").append(writer).append("\"");
        }
        // 수정일 기준으로 내림차순 정렬
        sql.append(" ORDER BY mod_date DESC");

        System.out.println(sql);

        return jdbcTemplate.query(sql.toString(), rowMapperToDto());
    }

    /**
     * 일정 수정. 수정일(mod_date)을 현재 시간으로 변경.
     *
     * @param id       일정 식별자
     * @param contents 내용
     * @param writer   작성자
     * @return 수정된 일정 개수를 반환
     */
    @Override
    public int updateSchedule(Long id, String contents, String writer) {

        LocalDate modDate = LocalDate.now();
        return jdbcTemplate.update("UPDATE schedule SET contents = ?, writer = ?, mod_date = ? WHERE id = ?", contents, writer, modDate, id);
    }

    /**
     * 일정 삭제
     *
     * @param id 일정 식별자
     */
    @Override
    public void deleteSchedule(Long id) {
        jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

    /**
     * db의 값을 응답 dto로 매핑
     *
     * @return 매핑된 dto 배열
     */
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

    /**
     * db의 값을 schedule 엔티티 형태로 매핑
     *
     * @return 매핑된 schedule 배열
     */
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

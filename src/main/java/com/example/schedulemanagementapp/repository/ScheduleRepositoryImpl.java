package com.example.schedulemanagementapp.repository;

import com.example.schedulemanagementapp.Paging;
import com.example.schedulemanagementapp.dto.ScheduleResponseDto;
import com.example.schedulemanagementapp.entity.Schedule;
import com.example.schedulemanagementapp.entity.User;
import com.example.schedulemanagementapp.exceptions.CustomException;
import com.example.schedulemanagementapp.exceptions.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
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
 * <li>description    : {@link ScheduleRepository} 구현체
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
     * @param schedule 값이 일부만 입력된 Schedule 객체
     * @param userId   유저 식별자
     * @return 생성한 일정을 담은 응답 dto
     */
    @Override
    public Schedule createSchedule(Schedule schedule, Long userId) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        LocalDate modDate = LocalDate.now();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("password", schedule.getPassword());
        parameters.put("contents", schedule.getContents());
        parameters.put("fk_userid", userId);
        parameters.put("mod_date", modDate);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findScheduleByIdOrElseThrow(key.longValue());
    }

    /**
     * 일정 단건 조회. 찾는 일정이 없을 경우 throw
     *
     * @param id 일정 식별자
     * @return 찾은 schedule 엔티티
     */
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", Schedule.rowMapperToSchedule(), id);
        return result.stream()
                .findAny()
                .orElseThrow(() -> {
                            Long max = findMaxId();
                            if (id < max) {
                                return new CustomException(ExceptionCode.DELETED_RESOURCE);
                            } else {
                                return new CustomException(ExceptionCode.INVALID_REQUEST);
                            }
                        }
                );
    }

    /**
     * 조건별 일정 다건 조회. 조건에 따라 sql문 생성 후 찾은 값을 dto 배열로 저장.
     *
     * @param date 날짜 조건
     * @param user 작성자 조건
     * @return 조건에 부합하는 일정 응답 dto 배열
     */
    @Override
    public List<Schedule> findAllScheduleByCond(LocalDate date, String user) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s.*, u.name FROM schedule s JOIN user u ON s.fk_userid=u.id");

        // 조건이 있을 시 WHERE 절 추가
        if (date != null || user != null) {
            sql.append(" WHERE");
        }

        // date 조건이 있는 경우
        boolean andFlag = false;
        if (date != null) {
            sql.append(" s.mod_date = ").append("'").append(date).append("'");
            andFlag = true;
        }

        // user 조건이 있는 경우
        if (user != null) {
            // 앞서 date 조건이 있으면 AND 추가
            if (andFlag) {
                sql.append(" AND");
            }
            sql.append(" u.name = ").append("\"").append(user).append("\"");
        }
        // 수정일 기준으로 내림차순 정렬
        sql.append(" ORDER BY mod_date DESC");

        return jdbcTemplate.query(sql.toString(), Schedule.rowMapperToSchedule());
    }

    /**
     * LIMIT을 사용해 페이징 객체의 값만큼 db에서 데이터 조회
     *
     * @param paging 페이지 시작 행 번호, 페이지 크기가 있는 객체
     * @return 페이징 단위의 데이터 리스트 반환
     */
    @Override
    public List<Schedule> findAllSchedulesInPage(Paging paging) {

        paging.setTotalSize(countTotalSize());
        System.out.println(paging.getTotalSize());

        return jdbcTemplate.query("SELECT s.*, u.name FROM schedule s JOIN user u ON s.fk_userid=u.id ORDER BY s.mod_date DESC LIMIT ?, ?", Schedule.rowMapperToSchedule(), paging.getStartRow(), paging.getPageSize());
    }

    /**
     * 일정 수정. 수정일(mod_date)을 현재 시간으로 변경.
     *
     * @param id       일정 식별자
     * @param contents 내용
     * @return 수정된 일정 개수를 반환
     */
    @Override
    public int updateSchedule(Long id, String contents) {

        LocalDate modDate = LocalDate.now();
        return jdbcTemplate.update("UPDATE schedule SET contents = ?, mod_date = ? WHERE id = ?", contents, modDate, id);
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

    @Override
    public int updateScheduleName(Long id, String name) {

        Long userId = findScheduleByIdOrElseThrow(id).getUserId();

        LocalDate modDate = LocalDate.now();
        return jdbcTemplate.update("UPDATE user SET name = ?, mod_date = ? WHERE id = ?", name, modDate, userId);
    }

    /**
     * 삭제 아이디 확인을 위해 가장 큰 아이디값을 구함
     *
     * @return schedule 테이블에서 가장 큰 아이디
     */
    private Long findMaxId() {
        /*List<Long> result = jdbcTemplate.query("SELECT id FROM schedule ORDER BY id DESC LIMIT 1", (rs, rowNum) -> rs.getLong("id"));
        return result.stream()
                .findAny()
                .orElseThrow();*/
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM schedule", (rs, rowNum)->rs.getLong("MAX(id)"));
    }

    private Long countTotalSize() {

        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM schedule", (rs, rowNum) -> rs.getLong("COUNT(id)"));
    }
}

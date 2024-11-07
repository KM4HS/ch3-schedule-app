package com.example.schedulemanagementapp.repository;

import com.example.schedulemanagementapp.dto.UserResponseDto;
import com.example.schedulemanagementapp.entity.User;
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
 * <li>fileName       : UserRepositoryImpl
 * <li>author         : daca0
 * <li>date           : 24. 11. 7.
 * <li>description    : {@link UserRepository} 구현체
 * </ul>
 * ===========================================================
 * <p>
 * 24. 11. 7.        daca0       최초 생성
 * </p>
 */

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 신규 유저 등록
     * @param user 유저 정보 일부가 담긴 User 객체
     * @return 등록된 유저 정보
     */
    @Override
    public UserResponseDto createUser(User user) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        LocalDate date = LocalDate.now();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", user.getEmail());
        parameters.put("name", user.getName());
        parameters.put("mod_date", date);
        parameters.put("reg_date", date);

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new UserResponseDto(key.longValue(), date, date, user.getEmail(), user.getName());
    }

    /**
     * 유저 조회
     * @param id 유저 식별자
     * @return 조회된 유저 정보
     */
    @Override
    public User findUserByIdOrElseThrow(Long id) {

        List<User> user = jdbcTemplate.query("SELECT * FROM user WHERE id = ?", rowMapperToUser(), id);

        return user.stream()
                .findAny()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id)
                );
    }

    /**
     * 유저 이름 수정
     * @param id 유저 식별자
     * @param name 변경할 이름
     * @return 변경된 유저 정보
     */
    @Override
    public int updateUser(Long id, String name) {

        LocalDate modDate = LocalDate.now();
        return jdbcTemplate.update("UPDATE user SET name = ?, mod_date = ? WHERE id = ?", name, modDate, id);
    }

    /**
     * db의 값을 User 형태로 매핑
     * @return 매핑된 user
     */
    private RowMapper<User> rowMapperToUser() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getDate("reg_date").toLocalDate(),
                        rs.getDate("mod_date").toLocalDate()
                );
            }
        };
    }
}

package ru.itis.javalab.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.itis.javalab.models.UserCookie;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CookiesRepositoryImpl implements CookiesRepository {

    //language=SQL
    private static final String SQL_SELECT = "select * from cookies";

    //language=SQL
    private static final String SQL_SELECT_BY_USER_ID = "select * from cookies where user_id = ?";

    //language=SQL
    private static final String SQ_SELECT_BY_COOKIE = "select * from cookies where cookie = ?";

    //language=SQL
    private static final String SQL_DELETE_BY_USER_ID = "delete from cookies where user_id = ?";

    //language=SQL
    private static final String SQL_INSERT_COOKIE = "insert into cookies (user_id, cookie)" +
            "values (?, ?)";

    //language=SQL
    private static final String SQL_UPDATE = "update cookies set user_id = ?, cookie = ?";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public CookiesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("cookies").usingColumns("user_id", "cookie");
    }

    private RowMapper<UserCookie> cookieRowMapper = (row, i) -> UserCookie.builder()
            .userId(row.getLong("user_id"))
            .userCookie(row.getString("cookie"))
            .build();


    @Override
    public UserCookie findUserCookieByUserId(Long id) {
        List<UserCookie> userCookies = jdbcTemplate.query(SQL_SELECT_BY_USER_ID, cookieRowMapper, id);
        return userCookies.isEmpty() ? null : userCookies.get(0);
    }

    @Override
    public UserCookie findUserCookieByCookie(String cookie) {
        List<UserCookie> userCookies = jdbcTemplate.query(SQ_SELECT_BY_COOKIE, cookieRowMapper, cookie);
        return userCookies.isEmpty() ? null : userCookies.get(0);
    }

    @Override
    public List<UserCookie> findAll() {
        return jdbcTemplate.query(SQL_SELECT, cookieRowMapper);
    }

    @Override
    public Optional<UserCookie> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Long save(UserCookie entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", entity.getUserId());
        params.put("cookie", entity.getUserCookie());
        simpleJdbcInsert.execute(params);
        return entity.getUserId();
    }

    @Override
    public void update(UserCookie entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getUserId(), entity.getUserCookie());
    }

    @Override
    public void deleteById(Long user_id) {
        jdbcTemplate.update(SQL_DELETE_BY_USER_ID, user_id);
    }

    @Override
    public void delete(UserCookie entity) {
        deleteById(entity.getUserId());
    }
}

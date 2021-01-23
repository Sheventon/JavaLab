package ru.itis.javalab.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.itis.javalab.models.Friend;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FriendsRepositoryImpl implements FriendsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Friend> rowMapper = (row, i) -> Friend.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("firstname"))
            .lastName(row.getString("lastname"))
            .age(row.getInt("age"))
            .description(row.getString("description"))
            .userId(row.getLong("user_id"))
            .build();

    public FriendsRepositoryImpl(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("friends")
                .usingColumns("firstname", "lastname", "age", "description", "user_id")
                .usingGeneratedKeyColumns("id");
    }

    //language=SQL
    private static final String SQL_SELECT = "select * from friends";

    //language=SQL
    private static final String SQL_SELECT_BY_USER_ID = "select * from friends where user_id = ?";

    //language=SQL
    private static final String SQL_UPDATE = "update friends set firstname = ?, lastname = ?, age = ?, username = ?, description = ?, user_id = ?";

    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from friends where id = ?";

    @Override
    public List<Friend> findByUserId(Long userId) {
        return jdbcTemplate.query(SQL_SELECT_BY_USER_ID, rowMapper, userId);
    }

    @Override
    public List<Friend> findAll() {
        return jdbcTemplate.query(SQL_SELECT, rowMapper);
    }

    @Override
    public Optional<Friend> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Long save(Friend entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstname", entity.getFirstName());
        params.put("lastname", entity.getLastName());
        params.put("age", entity.getAge());
        params.put("description", entity.getDescription());
        params.put("user_id", entity.getUserId());
        entity.setId((Long) simpleJdbcInsert.executeAndReturnKey(params));
        return entity.getId();
    }

    @Override
    public void update(Friend entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getFirstName(), entity.getLastName(), entity.getAge(),
                entity.getDescription(), entity.getUserId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public void delete(Friend entity) {
        deleteById(entity.getId());
    }
}

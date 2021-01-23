package ru.itis.javalab.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.itis.javalab.models.User;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<User> userRowMapper = (row, i) -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("firstname"))
            .lastName(row.getString("lastname"))
            .age(row.getInt("age"))
            .username(row.getString("username"))
            .password(row.getString("password"))
            .description(row.getString("description"))
            .isDeleted(row.getBoolean("is_deleted"))
            .build();


    //language=SQL
    private static final String SQL_SELECT_BY_AGE = "select * from users where age = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from users where id = ?";

    //language=SQL
    private static final String SQL_SELECT = "select * from users";

    //language=SQL
    private static final String SQL_SELECT_BY_USERNAME = "select * from users where username = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_FIRSTNAME_AND_LASTNAME = "select * from users where firstname = ? and lastname = ?";

    //language=SQL
    private static final String SQL_UPDATE = "update users set firstname = ?, lastname = ?, age = ?, username = ?, password = ?, description = ?, is_deleted = ? where id = ?";

    //language=SQL
    private static final String SQL_DELETE_BY_ID = "delete from users where id = ?";

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("users")
                .usingColumns("firstname", "lastname", "age", "username", "password", "description")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        return jdbcTemplate.query(SQL_SELECT_BY_AGE, userRowMapper, age);
    }

    @Override
    public Optional<User> findFirstByFirstnameAndLastname(String firstName, String lastName) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_SELECT_BY_FIRSTNAME_AND_LASTNAME, userRowMapper, firstName, lastName);
        } catch (EmptyResultDataAccessException e) {
            user = null;
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = jdbcTemplate.query(SQL_SELECT_BY_USERNAME, userRowMapper, username);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public boolean userIsExist(String username) {
        List<User> users = jdbcTemplate.query(SQL_SELECT_BY_USERNAME, userRowMapper, username);
        return !users.isEmpty();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_SELECT, userRowMapper);
    }

    @Override
    public Optional<User> findById(Long id) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            user = null;
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Long save(User entity) {
        Map<String, Object> params = new HashMap<>();
        params.put("firstname", entity.getFirstName());
        params.put("lastname", entity.getLastName());
        params.put("age", entity.getAge());
        params.put("username", entity.getUsername());
        params.put("password", entity.getPassword());
        params.put("description", entity.getDescription());
        entity.setId((Long) simpleJdbcInsert.executeAndReturnKey(params));
        return entity.getId();
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getFirstName(), entity.getLastName(),
                entity.getAge(), entity.getUsername(), entity.getPassword(), entity.getDescription(), entity.getIsDeleted(), entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public void delete(User entity) {
        deleteById(entity.getId());
    }
}

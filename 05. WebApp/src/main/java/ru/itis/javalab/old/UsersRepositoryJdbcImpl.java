package ru.itis.javalab.old;

import ru.itis.javalab.models.User;
import ru.itis.javalab.old.RowMapper;
import ru.itis.javalab.old.SimpleJdbcTemplate;
import ru.itis.javalab.repositories.UsersRepository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_AGE = "select * from users where age = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_AGE_AND_LASTNAME = "select * from users where age = ? and lastname = ?";

    //language=SQL
    private static final String SQL_SELECT = "select * from users";

    //language=SQL
    private static final String SQL_SELECT_BY_USERNAME = "select * from users where username = ?";

    //language=SQL
    private static final String SQL_INSERT = "insert into users (firstname, lastname, age, username, password)" +
            "VALUES (?, ?, ?, ?, ?)";

    private DataSource dataSource;

    private SimpleJdbcTemplate template;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.template = new SimpleJdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("firstname"))
            .lastName(row.getString("lastname"))
            .age(row.getInt("age"))
            .username(row.getString("username"))
            .password(row.getString("password"))
            .build();

    public List<User> findByAgeAndLastname(Integer age, String lastname) {
        return template.query(SQL_SELECT_BY_AGE_AND_LASTNAME, userRowMapper, age, lastname);
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        return template.query(SQL_SELECT_BY_AGE, userRowMapper, age);
    }

    @Override
    public Optional<User> findFirstByFirstnameAndLastname(String firstName, String lastName) {
        return Optional.empty();
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = template.query(SQL_SELECT_BY_USERNAME, userRowMapper, username);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public boolean userIsExist(String username) {
        List<User> users = template.query(SQL_SELECT_BY_USERNAME, userRowMapper, username);
        return !users.isEmpty();
    }

    @Override
    public List<User> findAll() {
        return template.query(SQL_SELECT, userRowMapper);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(User entity) {

    }
}

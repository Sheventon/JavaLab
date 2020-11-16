package ru.itis.javalab.homework;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class EntityManager {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public EntityManager(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // createTable("account", User.class);
    public <T> void createTable(String tableName, Class<T> entityClass) {
        // сгенерировать CREATE TABLE на основе класса
        // create table account ( id integer, firstName varchar(255), ...))

        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder query = new StringBuilder();
        query.append("create table ").append(tableName).append(" (").append("\n");
        for (Field field : fields) {
            String type = field.getType().getSimpleName();
            query.append(field.getName());
            switch (type) {
                case "int":
                case "Integer": {
                    query.append(" integer");
                    break;
                }
                case "long":
                case "Long": {
                    query.append(" bigint");
                    break;
                }
                case "short":
                case "Short": {
                    query.append(" smallint");
                    break;
                }
                case "boolean":
                case "Boolean": {
                    query.append(" boolean");
                    break;
                }
                case "String": {
                    query.append(" varchar(255)");
                    break;
                }
            }
            query.append(", ").append("\n");
        }
        query.delete(query.length() - 3, query.length() - 2).append(");");

        System.out.println(query);

        jdbcTemplate.execute(String.valueOf(query));
    }

    public void save(String tableName, Object entity) {
        // сканируем его поля
        // сканируем значения этих полей
        // генерируем insert into

        Class<?> classOfEntity = entity.getClass();
        Field[] fields = classOfEntity.getDeclaredFields();
        StringBuilder query = new StringBuilder();
        query.append("insert into ").append(tableName).append(" (");
        for (Field field : fields) {
            query.append(field.getName()).append(", ");
        }
        query.delete(query.length() - 2, query.length());

        query.append(")").append("\n").append("values ").append("(");
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(entity);
                if (field.getType() == String.class) {
                    query.append("'").append(value).append("'");
                } else {
                    query.append(field.get(entity));
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
            query.append(", ");
        }
        query.delete(query.length() - 2, query.length()).append(")");

        System.out.println(query);

        jdbcTemplate.execute(String.valueOf(query));
    }

        // User user = entityManager.findById("account", User.class, Long.class, 10L);
        public <T, ID> T findById(String tableName, Class<T> resultType, Class<ID> idType, ID idValue) {
            // сгенеририровать select

            //String query = "select * from " + tableName + " where " + "id" + " = " + idValue;
            String query = String.format("select * from %s where id = %s", tableName, idValue);
            System.out.println(query);

            Field[] f = resultType.getDeclaredFields();
            Class<?>[] types = new Class<?>[f.length];
            for(int i = 0; i < types.length; i++) {
                types[i] = f[i].getType();
            }

            Object[] parameters = new Object[types.length];
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();
                Field[] fields = resultType.getDeclaredFields();
                if(resultSet.next()) {
                    for (int i = 0; i < fields.length; i++) {
                        parameters[i] = resultSet.getObject(i + 1);
                    }
                }
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
            try {
                Constructor<?> constructor = resultType.getConstructor(types);
                Object object = constructor.newInstance(parameters);
                return (T) object;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        }
    }

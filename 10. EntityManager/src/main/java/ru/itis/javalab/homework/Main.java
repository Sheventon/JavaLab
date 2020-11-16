package ru.itis.javalab.homework;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.javalab.homework.models.User;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/db.properties"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("db.jdbc.url"));
        hikariConfig.setDriverClassName(properties.getProperty("db.jdbc.driver-class-name"));
        hikariConfig.setUsername(properties.getProperty("db.jdbc.username"));
        hikariConfig.setPassword(properties.getProperty("db.jdbc.password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.jdbc.hikari.max-pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        EntityManager entityManager = new EntityManager(dataSource);
        //entityManager.createTable("userss", User.class);
        User user = new User(1L, "Anton", "Sheverda", false);
        //entityManager.save("userss", user);

        User user1 = entityManager.findById("userss", User.class, Long.class, 1L);
        System.out.println(user1.toString());
    }
}

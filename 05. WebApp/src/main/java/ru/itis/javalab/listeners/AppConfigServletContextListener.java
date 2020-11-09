package ru.itis.javalab.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.config.ApplicationConfig;
import ru.itis.javalab.repositories.CookieRepository;
import ru.itis.javalab.repositories.CookieRepositoryImpl;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.old.UsersRepositoryJdbcImpl;
import ru.itis.javalab.repositories.UsersRepositoryJdbcTemplateImpl;
import ru.itis.javalab.services.CookieService;
import ru.itis.javalab.services.CookieServiceImpl;
import ru.itis.javalab.services.UsersService;
import ru.itis.javalab.services.UsersServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class AppConfigServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        //ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        servletContext.setAttribute("applicationContext", context);

        /*Properties properties = new Properties();
        try {
            properties.load(servletContext.getResourceAsStream("/WEB-INF/properties/db.properties"));
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

        //UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        UsersRepository usersRepository = new UsersRepositoryJdbcTemplateImpl(dataSource);

        //UsersService usersService = new UsersServiceImpl(usersRepository);

        CookieRepository cookieRepository = new CookieRepositoryImpl(dataSource);
        CookieService cookieService = new CookieServiceImpl(usersRepository, cookieRepository);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UsersService usersService = new UsersServiceImpl(usersRepository, passwordEncoder);

        servletContext.setAttribute("dataSource", dataSource);
        servletContext.setAttribute("usersService", usersService);
        servletContext.setAttribute("cookieService", cookieService);
        servletContext.setAttribute("passwordEncoder", passwordEncoder);*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

package ru.itis.javalab.servlets;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.context.ApplicationContext;
import ru.itis.javalab.models.Friend;
import ru.itis.javalab.models.User;
import ru.itis.javalab.services.FriendsService;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/about_me")
public class AboutMeServlet extends HttpServlet {

    private UsersService usersService;
    private FriendsService friendsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("applicationContext");
        usersService = applicationContext.getBean(UsersService.class);
        friendsService = applicationContext.getBean(FriendsService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(new FileTemplateLoader(new File("C:\\ITIS\\JavaLab\\05. WebApp\\src\\main\\webapp\\ftlh")));

        resp.setContentType("text/html");
        HttpSession session = req.getSession();

        Template template = configuration.getTemplate("about_me.ftlh");
        String username = (String) session.getAttribute("user");
        User user = usersService.getByUsername(username);
        List<Friend> friends = friendsService.getByUserId(user.getId());
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("user", user);
        attributes.put("friends", friends);

        try {
            template.process(attributes, resp.getWriter());
        } catch (TemplateException e) {
            throw new IllegalStateException(e);
        }
    }
}

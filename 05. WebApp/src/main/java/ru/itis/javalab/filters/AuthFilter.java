package ru.itis.javalab.filters;

import org.springframework.context.ApplicationContext;
import ru.itis.javalab.services.CookiesService;
import ru.itis.javalab.services.UsersService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    private UsersService usersService;
    private CookiesService cookiesService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("applicationContext");
        usersService = applicationContext.getBean(UsersService.class);
        cookiesService = applicationContext.getBean(CookiesService.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        if (!uri.equals("/login")) {
            if (session  == null || session.getAttribute("user") == null) {
                response.sendRedirect("/login");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

        /*Cookie[] cookies = request.getCookies();
        String auth = null;
        if (session.getAttribute("user") == null) {
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("Auth")) {
                        auth = c.getValue();
                        break;
                    }
                }
            }
        }
        if (auth != null) {
            Long id = cookiesService.getCookieByAuth(auth).getUserId();
            String username = usersService.getById(id).get().getUsername();
            request.getSession().setAttribute("user", username);
        }*/
    }

    @Override
    public void destroy() {

    }
}

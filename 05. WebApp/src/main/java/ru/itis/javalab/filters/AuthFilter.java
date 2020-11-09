package ru.itis.javalab.filters;

import ru.itis.javalab.models.UserCookie;
import ru.itis.javalab.services.CookieService;
import ru.itis.javalab.services.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        /*ServletContext servletContext = config.getServletContext();
        servletContext.getAttribute("usersService");
        servletContext.getAttribute("cookieService");*/
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();
        String auth = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("Auth")) {
                    auth = c.getValue();
                }
            }
        }

        if(auth == null) {
            if(!request.getRequestURI().equals("/login")) {
                response.sendRedirect("/login");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

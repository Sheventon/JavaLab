/*
package ru.itis.javalab.filters;

import ru.itis.javalab.services.CookieService;
import ru.itis.javalab.services.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/login"})
public class SessionFilter implements Filter {

    private UsersService usersService;
    private CookieService cookieService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        servletContext.getAttribute("usersService");
        servletContext.getAttribute("cookieService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
*/

package ru.itis.javalab.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class CsrfFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getMethod().equals("POST")) {
            String requestCsrf = request.getParameter("csrf_token");
            String sessionCsrf = (String) request.getSession(false).getAttribute("csrf_token");

            System.out.println("requestCsrf: " + requestCsrf);
            System.out.println("sessionCsrf: " + sessionCsrf);

            if (sessionCsrf.equals(requestCsrf)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.setStatus(403);
            }
            return;
        }


        if (request.getMethod().equals("GET")) {
            String csrf = UUID.randomUUID().toString();
            request.setAttribute("csrf_token", csrf);
            request.getSession().setAttribute("csrf_token", csrf);
            //tokens.add(csrf);

            System.out.println(request.getAttribute("csrf_token"));
            System.out.println(request.getSession().getAttribute("csrf_token"));
            /*System.out.println("TOKENS: {");
            for(String s : tokens) {
                System.out.println(s);
            }
            System.out.println("}");*/

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }
}


package ru.itis.javalab.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CsrfInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("POST")) {
            String requestCsrf = request.getParameter("csrf_token");
            String sessionCsrf = (String) request.getSession(false).getAttribute("csrf_token");
            if (!sessionCsrf.equals(requestCsrf)) {
                response.setStatus(403);
                return false;
            }
            return true;
        }

        if (request.getMethod().equals("GET")) {
            String csrf = UUID.randomUUID().toString();
            request.setAttribute("csrf_token", csrf);
            request.getSession().setAttribute("csrf_token", csrf);
        }
        return true;
    }
}

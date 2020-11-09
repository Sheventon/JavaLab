package ru.itis.javalab.services;

import ru.itis.javalab.models.UserCookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CookieService {
    void generateCookieByUserId(HttpServletRequest req, HttpServletResponse resp, String username);
    UserCookie getCookieByAuth(String cookie);
}

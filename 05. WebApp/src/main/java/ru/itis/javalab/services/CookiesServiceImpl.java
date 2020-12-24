package ru.itis.javalab.services;

import ru.itis.javalab.models.UserCookie;
import ru.itis.javalab.repositories.CookiesRepository;
import ru.itis.javalab.repositories.UsersRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CookieServiceImpl implements CookieService {

    private UsersRepository usersRepository;
    private CookiesRepository cookiesRepository;

    public CookieServiceImpl(UsersRepository usersRepository, CookiesRepository cookiesRepository) {
        this.usersRepository = usersRepository;
        this.cookiesRepository = cookiesRepository;
    }

    @Override
    public void generateCookieByUserId(HttpServletRequest req, HttpServletResponse resp, String username) {
        Long userId = usersRepository.findByUsername(username).getId();
        String auth;
        UserCookie userCookie = cookiesRepository.findUserCookieByUserId(userId);
        if(userCookie != null) {
            auth = userCookie.getUserCookie();
        } else {
            do {
                auth = UUID.randomUUID().toString();
            } while (cookiesRepository.findUserCookieByUserId(userId) != null);
            UserCookie newUserCookie = UserCookie.builder()
                    .userId(userId)
                    .userCookie(auth)
                    .build();

            cookiesRepository.save(newUserCookie);
        }
        Cookie cookie = new Cookie("Auth", auth);
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    @Override
    public UserCookie getCookieByAuth(String cookie) {
        return cookiesRepository.findUserCookieByCookie(cookie);
    }

    @Override
    public UserCookie getCookieByUserId(Long userId) {
        return cookiesRepository.findUserCookieByUserId(userId);
    }
}
